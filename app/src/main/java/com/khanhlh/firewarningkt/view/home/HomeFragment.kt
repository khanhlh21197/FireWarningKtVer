package com.khanhlh.firewarningkt.view.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.khanhlh.firewarningkt.R
import com.khanhlh.firewarningkt.data.remote.user.UserService
import com.khanhlh.firewarningkt.data.repository.UserRepository
import com.khanhlh.firewarningkt.databinding.FragmentHomeBinding
import com.khanhlh.firewarningkt.helper.extens.set
import com.khanhlh.firewarningkt.view.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private lateinit var mViewModel: HomeViewModel

    companion object {
        fun newInstance(): HomeFragment {
            val args: Bundle = Bundle()

            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun initView() {
//        mViewModel = HomeViewModel(repository)
        mBinding.vm = mViewModel
    }

    override fun loadData(isRefresh: Boolean) {

    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun onClick(v: View?) {

    }
}
