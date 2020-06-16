package com.khanhlh.firewarningkt.data.remote.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CaptainEyeResponse(
    @SerializedName("success")
    @Expose
    val success: String,

    @SerializedName("code")
    @Expose
    val code: String,

    @SerializedName("data")
    @Expose
    val data: String,

    @SerializedName("message")
    @Expose
    val message: String
)