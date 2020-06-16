package com.khanhlh.firewarningkt.data.remote

import android.app.Activity
import android.text.TextUtils
import android.util.Log
import com.khanhlh.firewarningkt.R
import com.khanhlh.firewarningkt.data.remote.model.BaseResponse
import com.khanhlh.firewarningkt.helper.converter.JsonConverter
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.util.*

class MqttService(private val activity: Activity) {
    private val PRODUCTKEY = "a11xsrW****"
    private val DEVICENAME = "paho_android"
    private val DEVICESECRET = "tLMT9QWD36U2SArglGqcHCDK9rK9****"
    private var receiveMessage: ReceiveMessage? = null
    private var clientId: String = UUID.randomUUID().toString()
    private val mqttConnectOptions: MqttConnectOptions

    fun setReceiveMessage(receiveMessage: ReceiveMessage?) {
        this.receiveMessage = receiveMessage
    }

    fun publishMessage(pubTopic: String?, o: Any?) {
        try {
            val mqttAndroidClient =
                MqttAndroidClient(activity, host, clientId)
            mqttAndroidClient.connect(mqttConnectOptions, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    Log.i(TAG, "connect succeed")
                    try {
                        val message = MqttMessage()
                        val payload: String = JsonConverter.objectToJson(o)
                        message.payload = payload.toByteArray()
                        message.qos = 0
                        mqttAndroidClient.publish(pubTopic, message, null, publishListener)
                    } catch (e: MqttException) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(
                    asyncActionToken: IMqttToken,
                    exception: Throwable
                ) {
                    Log.i(TAG, "connect failed")
                }
            })
        } catch (e: MqttException) {
            Log.e(TAG, e.toString())
            e.printStackTrace()
        }
    }

    fun subscribeTopic(topic: String?) {
        /* Establish an MQTT connection */
        try {
            val mqttAndroidClient =
                MqttAndroidClient(activity, host, clientId)
            mqttAndroidClient.connect(mqttConnectOptions, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    Log.i(TAG, "connect succeed")
                    try {
                        mqttAndroidClient.subscribe(topic, 0, iMqttMessageListener)
                        //                        mqttAndroidClient.subscribe(topic, 0, null, subscribeListener);
                    } catch (e: MqttException) {
                        e.printStackTrace()
                        receiveMessage!!.onSubError(e.toString())
                    }
                }

                override fun onFailure(
                    asyncActionToken: IMqttToken,
                    exception: Throwable
                ) {
                    Log.i(TAG, "connect failed")
                    receiveMessage!!.onSubError(exception.toString())
                }
            })
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun unSubscribe(topic: String?) {
        try {
            val mqttAndroidClient =
                MqttAndroidClient(activity, host, clientId)
            mqttAndroidClient.unsubscribe(topic)
        } catch (e: MqttException) {
            e.printStackTrace()
        }
    }

    fun unRegister() {
        val mqttAndroidClient =
            MqttAndroidClient(activity, host, clientId)
        mqttAndroidClient.unregisterResources()
        mqttAndroidClient.close()
    }

    private val publishListener: IMqttActionListener = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken) {
            Log.i(TAG, "publish succeed! ")
            receiveMessage!!.onPubSuccess(asyncActionToken.toString())
        }

        override fun onFailure(
            asyncActionToken: IMqttToken,
            exception: Throwable
        ) {
            Log.i(TAG, "publish failed!")
            receiveMessage!!.onPubError(exception.toString())
        }
    }
    private val subscribeListener: IMqttActionListener = object : IMqttActionListener {
        override fun onSuccess(asyncActionToken: IMqttToken) {
            Log.i(TAG, "subscribed succeed")
        }

        override fun onFailure(
            asyncActionToken: IMqttToken,
            exception: Throwable
        ) {
            Log.i(TAG, "subscribed failed")
        }
    }
    private val iMqttMessageListener =
        label@ IMqttMessageListener { topic: String?, message: MqttMessage? ->
            if (message == null) {
                receiveMessage!!.onSubError(null)
                return@label
            }
            val mess = String(message.payload)
            receiveMessage!!.onLoading(true)
            if (!TextUtils.isEmpty(mess)) {
                val response: BaseResponse = JsonConverter.jsonToObject(
                    mess,
                    BaseResponse::class.java
                ) as BaseResponse
                receiveMessage!!.onSubSuccess(response)
            } else {
                receiveMessage!!.onSubError(activity.getString(R.string.exception))
            }
            Log.d(TAG, String(message.payload))
        }

    interface ReceiveMessage {
        fun onSubSuccess(response: BaseResponse?)
        fun onSubError(message: String?)
        fun onPubSuccess(message: String?)
        fun onPubError(message: String?)
        fun onLoading(loading: Boolean)
    }

    companion object {
        private var userName = ""
        private var passWord = ""
        private const val host = "tcp://192.168.2.13:1234"
        private const val TAG = "MqttClient"
    }

    init {
        /* Obtain the MQTT connection information clientId, username, and password. */
        val aiotMqttOption: AIotMqttOption =
            AIotMqttOption().getMqttOption(PRODUCTKEY, DEVICENAME, DEVICESECRET)!!
        clientId = aiotMqttOption.clientId
        userName = aiotMqttOption.username
        passWord = aiotMqttOption.password

        /* Create an MqttConnectOptions object and configure the username and password. */mqttConnectOptions =
            MqttConnectOptions()
        mqttConnectOptions.userName = userName
        mqttConnectOptions.password = passWord.toCharArray()

        /* Create an MqttAndroidClient object and set a callback interface. */

//        MqttAndroidClient mqttAndroidClient = new MqttAndroidClient(activity, host, clientId);
//        mqttAndroidClient.setCallback(mqttCallBack);
    }
}