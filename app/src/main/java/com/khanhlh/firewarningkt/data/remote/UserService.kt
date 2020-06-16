package com.khanhlh.firewarningkt.data.remote

import com.khanhlh.firewarningkt.data.local.model.User
import com.khanhlh.firewarningkt.data.remote.model.BaseResponse
import com.khanhlh.firewarningkt.data.remote.model.CaptainEyeResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserService {
    companion object {
        const val REGISTER_CAPTAIN_EYE = "account/register";
        fun create(): UserService {
            return APIService.getClient().create(UserService::class.java)
        }
    }

    fun getAllUsers(): Observable<BaseResponse>

    fun login(email: String, password: String): Single<BaseResponse>

    fun register(): Single<BaseResponse>

    @POST(REGISTER_CAPTAIN_EYE)
    fun registerCaptainEye(@Body user: User): Single<CaptainEyeResponse>
}