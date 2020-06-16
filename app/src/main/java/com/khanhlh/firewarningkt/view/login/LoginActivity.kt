package com.khanhlh.firewarningkt.view.login

import android.view.View
import com.khanhlh.firewarningkt.R
import com.khanhlh.firewarningkt.data.remote.UserService
import com.khanhlh.firewarningkt.data.repository.UserRepository
import com.khanhlh.firewarningkt.databinding.ActivityLoginBinding
import com.khanhlh.firewarningkt.esptouch.EspTouchActivity
import com.khanhlh.firewarningkt.helper.extens.openActivity
import com.khanhlh.firewarningkt.helper.extens.set
import com.khanhlh.firewarningkt.view.base.BaseActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    private lateinit var mViewModel: LoginViewModel;
    private lateinit var repo: UserRepository
    private lateinit var service: UserService

    override fun initView() {
        service = UserService.create()
        repo = UserRepository(service)
        mViewModel = LoginViewModel(repo)
        mBinding.vm = mViewModel
    }

    override fun loadData(isRefresh: Boolean) {
    }

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogin -> mViewModel
                .registerCaptainEye()
                .subscribe({ onRegisterSuccess(it) }, { onFailure(it) })
            R.id.tvCreateAccount -> mViewModel.showRegister.set(true)
            R.id.tvBackToLogin -> mViewModel.showRegister.set(false)
        }
    }

    private fun onFailure(action: Throwable?) {
        if (action != null) {
            toastFailure(action)
            openActivity(EspTouchActivity::class.java)
        }
    }

    private fun onRegisterSuccess(it: Any?) {
        toastSuccess(it.toString())
        openActivity(EspTouchActivity::class.java)
    }
}