package com.khanhlh.firewarningkt.data.remote.user

import com.khanhlh.firewarningkt.constant.Constants
import com.khanhlh.firewarningkt.data.local.model.User
import com.khanhlh.firewarningkt.data.remote.model.BaseResponse
import com.khanhlh.firewarningkt.data.remote.model.CaptainEyeResponse
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    companion object {
        private val okHttpClient = OkHttpClient.Builder().build()
        const val REGISTER_CAPTAIN_EYE = "account/register";
        fun create(): UserService {
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(UserService::class.java)
        }
    }

    fun getAllUsers(): Observable<BaseResponse>

    fun login(email: String, password: String): Single<BaseResponse>

    fun register(): Single<BaseResponse>

    @POST(REGISTER_CAPTAIN_EYE)
    fun registerCaptainEye(@Body user: User): Single<CaptainEyeResponse>
}