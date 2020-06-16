package com.khanhlh.firewarningkt.data.remote.mqtt

import java.math.BigInteger
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class AIotMqttOption {
    var username = ""

    var password = ""

    var clientId = ""

    fun getMqttOption(
        productKey: String?,
        deviceName: String?,
        deviceSecret: String?
    ): AIotMqttOption? {
        if (productKey == null || deviceName == null || deviceSecret == null) {
            return null
        }
        try {
            val timestamp =
                System.currentTimeMillis().toString()

            // clientId
            this.clientId = productKey + "." + deviceName + "|timestamp=" + timestamp +
                    ",_v=paho-android-1.0.0,securemode=2,signmethod=hmacsha256|"

            // userName
            username = "$deviceName&$productKey"

            // password
            val macSrc = "clientId" + productKey + "." + deviceName + "deviceName" +
                    deviceName + "productKey" + productKey + "timestamp" + timestamp
            val algorithm = "HmacSHA256"
            val mac = Mac.getInstance(algorithm)
            val secretKeySpec =
                SecretKeySpec(deviceSecret.toByteArray(), algorithm)
            mac.init(secretKeySpec)
            val macRes = mac.doFinal(macSrc.toByteArray())
            password = String.format("%064x", BigInteger(1, macRes))
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return this
    }
}