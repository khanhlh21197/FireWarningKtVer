package com.khanhlh.firewarningkt.helper.converter

import com.google.gson.Gson

object JsonConverter {
    fun objectToJson(o: Any?): String {
        val gson = Gson()
        return gson.toJson(o)
    }

    fun <T> jsonToObject(json: String?, tClass: Class<T>?): Any {
        val gson = Gson()
        return gson.fromJson(json, tClass)!!
    }
}