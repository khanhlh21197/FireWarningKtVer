package com.khanhlh.firewarningkt.data.repository

import com.khanhlh.firewarningkt.data.local.model.User
import com.khanhlh.firewarningkt.data.remote.UserService
import com.khanhlh.firewarningkt.data.remote.model.BaseResponse
import com.khanhlh.firewarningkt.data.remote.model.CaptainEyeResponse
import io.reactivex.Single
import javax.inject.Singleton

@Singleton
class UserRepository(
    private val userService: UserService
) {
    fun login(email: String, password: String): Single<BaseResponse> =
        userService.login(email, password)

    fun register(
        email: String,
        password: String,
        name: String,
        address: String
    ): Single<BaseResponse> = userService.register()

    fun registerCaptainEye(user: User): Single<CaptainEyeResponse> =
        userService.registerCaptainEye(user)
}