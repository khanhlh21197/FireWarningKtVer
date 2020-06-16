package com.khanhlh.firewarningkt.data.remote.mqtt

import android.content.Context
import android.util.Log
import net.eusashead.iot.mqtt.ObservableMqttClient
import net.eusashead.iot.mqtt.paho.PahoObservableMqttClient
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*

object MqttClient {
    private lateinit var mqttAndroidClient: MqttAndroidClient

    fun connect(applicationContext : Context) {
        mqttAndroidClient = MqttAndroidClient ( applicationContext,"YOUR MQTT BROKER ADDRESS","YOUR CLIENT ID" )
        try {
            val token = mqttAndroidClient.connect()
            token.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken)                        {
                    Log.i("Connection", "success ")
                    //connectionStatus = true
                    // Give your callback on connection established here
                }
                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    //connectionStatus = false
                    Log.i("Connection", "failure")
                    // Give your callback on connection failure here
                    exception.printStackTrace()
                }
            }
        } catch (e: MqttException) {
            // Give your callback on connection failure here
            e.printStackTrace()
        }
    }

    fun subscribe(topic: String) {
        val qos = 2 // Mention your qos value
        try {
            mqttAndroidClient.subscribe(topic, qos, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    // Give your callback on Subscription here
                }
                override fun onFailure(
                    asyncActionToken: IMqttToken,
                    exception: Throwable
                ) {
                    // Give your subscription failure callback here
                }
            })
        } catch (e: MqttException) {
            // Give your subscription failure callback here
        }
    }

    fun unSubscribe(topic: String) {
        try {
            val unsubToken = mqttAndroidClient.unsubscribe(topic)
            unsubToken.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    // Give your callback on unsubscribing here
                }
                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    // Give your callback on failure here
                }
            }
        } catch (e: MqttException) {
            // Give your callback on failure here
        }
    }

    fun receiveMessages() {
        mqttAndroidClient.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable) {
                //connectionStatus = false
                // Give your callback on failure here
            }
            override fun messageArrived(topic: String, message: MqttMessage) {
                try {
                    val data = String(message.payload, charset("UTF-8"))
                    // data is the desired received message
                    // Give your callback on message received here
                } catch (e: Exception) {
                    // Give your callback on error here
                }
            }
            override fun deliveryComplete(token: IMqttDeliveryToken) {
                // Acknowledgement on delivery complete
            }
        })
    }

    fun publish(topic: String, data: String) {
        val encodedPayload : ByteArray
        try {
            encodedPayload = data.toByteArray(charset("UTF-8"))
            val message = MqttMessage(encodedPayload)
            message.qos = 2
            message.isRetained = false
            mqttAndroidClient.publish(topic, message)
        } catch (e: Exception) {
            // Give Callback on error here
        } catch (e: MqttException) {
            // Give Callback on error here
        }
    }

    fun disconnect() {
        try {
            val disconToken = mqttAndroidClient.disconnect()
            disconToken.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    //connectionStatus = false
                    // Give Callback on disconnection here
                }
                override fun onFailure(
                    asyncActionToken: IMqttToken,
                    exception: Throwable
                ) {
                    // Give Callback on error here
                }
            }
        } catch (e: MqttException) {
            // Give Callback on error here
        }
    }
}