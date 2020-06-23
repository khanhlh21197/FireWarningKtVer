package com.khanhlh.firewarningkt.view.gallery

import android.annotation.SuppressLint
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.khanhlh.firewarningkt.MyBounceInterpolator
import com.khanhlh.firewarningkt.R
import com.khanhlh.firewarningkt.databinding.FragmentGalleryBinding
import com.khanhlh.firewarningkt.view.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_gallery.*


class GalleryFragment : BaseFragment<FragmentGalleryBinding>() {

    private lateinit var galleryViewModel: GalleryViewModel
    private var mAutoIncrement = false
    private var mAutoDecrement = false
    var mValue = 0
    val repeatUpdateHandler = Handler()
    lateinit var anim: Animation
    lateinit var blink: Animation
    val interpolator = MyBounceInterpolator(0.2, 20.0)

    override fun initView() {
        anim = AnimationUtils.loadAnimation(activity, R.anim.bounce)
        blink = AnimationUtils.loadAnimation(activity, R.anim.blink)

        anim.interpolator = interpolator
        galleryViewModel = GalleryViewModel()
        mBinding.vm = galleryViewModel
        onLongClick()
        croller.setOnProgressChangedListener {
            croller.label = (it + 17).toString() + " " + 0x00B0.toChar()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onLongClick() {
        plus.setOnLongClickListener {
            mAutoIncrement = true
            repeatUpdateHandler.post(RptUpdater())
            true
        }

        plus.setOnTouchListener { v, event ->
            if ((event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL)
                && mAutoIncrement
            ) mAutoIncrement = false
            false
        }

        minus.setOnTouchListener { v, event ->
            if ((event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL)
                && mAutoDecrement
            ) mAutoDecrement = false
            false
        }

        minus.setOnLongClickListener {
            mAutoDecrement = true
            repeatUpdateHandler.post(RptUpdater())
            true
        }
    }

    override fun loadData(isRefresh: Boolean) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_gallery
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.plus -> {
                startAnimation()
                plus.startAnimation(anim)
                increment()
            }
            R.id.minus -> {
                startAnimation()
                decrement()
                minus.startAnimation(anim)
            }
            R.id.cool -> {
                cool.startAnimation(blink)
                startAnimation()
            }
            R.id.hot -> {
                hot.startAnimation(blink)
                startAnimation()
            }
            R.id.air -> {
                air.startAnimation(blink)
                startAnimation()
            }
        }
    }

    inner class RptUpdater : Runnable {
        override fun run() {
            if (mAutoIncrement) {
                increment()
                repeatUpdateHandler.postDelayed(RptUpdater(), REP_DELAY)
            } else if (mAutoDecrement) {
                decrement()
                repeatUpdateHandler.postDelayed(RptUpdater(), REP_DELAY)
            }
        }
    }

    fun decrement() {
        mValue--
        if (croller.progress > croller.min) croller.progress--
    }

    fun increment() {
        mValue++
        if (croller.progress < croller.max) croller.progress++
    }

    companion object {
        const val REP_DELAY = 1L
    }

    private fun startAnimation() {
        airConditioner.startAnimation(blink)
        Handler().postDelayed({ airConditioner.clearAnimation() }, 1000)
    }
}
