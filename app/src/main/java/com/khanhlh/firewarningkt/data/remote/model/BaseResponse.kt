package com.khanhlh.firewarningkt.data.remote.model

data class BaseResponse(
    var errorCode: String? = null,
    var result: String? = null,
    var message: String? = null
)