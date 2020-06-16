package com.khanhlh.firewarningkt.view.home

import androidx.lifecycle.MutableLiveData
import com.khanhlh.firewarningkt.data.local.model.User
import com.khanhlh.firewarningkt.data.remote.model.CaptainEyeResponse
import com.khanhlh.firewarningkt.data.repository.UserRepository
import com.khanhlh.firewarningkt.helper.extens.*
import com.khanhlh.firewarningkt.viewmodel.BaseViewModel
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class HomeViewModel
constructor(private val repo: UserRepository) : BaseViewModel() {
    private val PASSWORD_PATTERN = "^[a-zA-Z0-9_]{6,16}$"
    private val EMAIL_PATTERN =
        "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$"

    private val emailPattern = Pattern.compile(EMAIL_PATTERN)
    private val passwordPattern = Pattern.compile(PASSWORD_PATTERN)

    private val isBtnEnabled = MutableLiveData<Boolean>().init(false)
    private val loginVisibility = MutableLiveData<Boolean>().init(true)
    private val logoutVisibility = MutableLiveData<Boolean>().init(false)
    private val showLogin = MutableLiveData<Boolean>().init(true)
    private val showLogout = MutableLiveData<Boolean>().init(false)
    val showRegister = MutableLiveData<Boolean>().init(false)

    val email = MutableLiveData<String>().init("")
    val password = MutableLiveData<String>().init("")
    val rePassword = MutableLiveData<String>().init("")
    val name = MutableLiveData<String>().init("")
    val address = MutableLiveData<String>().init("")


    init {
        Flowable.combineLatest(
            password.toFlowable<String>(),
            email.toFlowable<String>(),
            BiFunction<String, String, Boolean> { t1, t2 ->
                return@BiFunction !(!emailPattern.matcher(t2).matches()
                        || !passwordPattern.matcher(t1).matches())
            }).doOnNext { isBtnEnabled.set(it) }.subscribe()

        showLogin.toFlowable().doOnNext {
            loading.set(!(showLogin.get(false) || showLogout.get(false)))
            loginVisibility.set(showLogin.get(false))
        }.subscribe()

        showLogout.toFlowable().doOnNext {
            loading.set(!(showLogin.get(false) || showLogout.get(false)))
            logoutVisibility.set(showLogout.get(false))
        }.subscribe()
    }

    fun attemptToLogIn() = repo.login(email.get() ?: "", password.get() ?: "")
        .subscribeOn(Schedulers.io())
        .delay(1, TimeUnit.SECONDS)
        .getOriginData()
//        .flatMap { repo.myProfile().map { t: UserModel -> t.model } }
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe {
            logD(it.toString())
            showLogin.set(false)
            showLogout.set(false)
        }
        .doAfterTerminate { showLogin.set(true) }

//    fun attemptToLogout() = repo.logout().getOriginData().async(1000)
//        .doOnSubscribe {
//            showLogin.set(false)
//            showLogout.set(false)
//        }
//        .doFinally { showLogout.set(true) }

    fun attempToRegister() = repo.register(
        email.get() ?: "",
        password.get() ?: "",
        name.get() ?: "",
        address.get() ?: ""
    ).subscribeOn(Schedulers.io())
        .delay(1, TimeUnit.SECONDS)
        .getOriginData()
        .doOnSubscribe {
            logD(it.toString())
            showLogin.set(false)
            showLogout.set(false)
        }
        .doAfterTerminate { showLogin.set(true) }

    fun registerCaptainEye() =
        repo.registerCaptainEye(User(email = email.get()!!, password = password.get()!!))
            .subscribeOn(Schedulers.io())
            .delay(1, TimeUnit.SECONDS)
            .compose { upstream ->
                upstream.flatMap { t: CaptainEyeResponse ->
                    if (t.success.equals(true)) {
                        return@flatMap Single.just(t)
                    } else {
                        return@flatMap Single.error<String>(Throwable(message = t.message))
                    }
                }
            }
            .doOnSubscribe {
                logD(it.toString())
                startLoad()
                startBlur()
            }
            .doAfterTerminate {
                stopLoad()
                stopBlur()
            }
}