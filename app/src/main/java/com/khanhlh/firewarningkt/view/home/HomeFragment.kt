package com.khanhlh.firewarningkt.view.home

import android.view.View
import com.khanhlh.firewarningkt.R
import com.khanhlh.firewarningkt.data.remote.UserService
import com.khanhlh.firewarningkt.data.repository.UserRepository
import com.khanhlh.firewarningkt.databinding.FragmentHomeBinding
import com.khanhlh.firewarningkt.helper.extens.set
import com.khanhlh.firewarningkt.view.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private lateinit var mViewModel: HomeViewModel
    private lateinit var repository: UserRepository
    private lateinit var userService: UserService
//    private lateinit var userDao: UserDao

    override fun initView() {
        userService = UserService.create()
        repository = UserRepository(userService)
        mViewModel = HomeViewModel(repository)
        mBinding.vm = mViewModel
    }

    private fun onFailure(action: Throwable?) {
        if (action != null) {
            toastFailure(action)
        }
    }

    private fun onRegisterSuccess(it: Any?) {
        toastSuccess(it.toString())
    }

    override fun loadData(isRefresh: Boolean) {

    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogin -> mViewModel
                .registerCaptainEye()
                .subscribe({ onRegisterSuccess(it) }, { onFailure(it) })
            R.id.tvCreateAccount -> mViewModel.showRegister.set(true)
            R.id.tvBackToLogin -> mViewModel.showRegister.set(false)
        }
    }
}
