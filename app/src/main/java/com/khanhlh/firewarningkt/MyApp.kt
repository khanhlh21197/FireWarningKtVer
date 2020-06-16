package com.khanhlh.firewarningkt

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.net.wifi.WifiManager
import android.os.Build
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.*

class MyApp : Application() {
    private var mResourceProvider: ResourceProvider? = null
    fun getResourceProvider(): ResourceProvider? {
        if (mResourceProvider == null) mResourceProvider = ResourceProvider(this)
        return mResourceProvider
    }

    private var app: MyApp? = null

    private var mBroadcastData: MutableLiveData<String>? = null

    private var mCacheMap: MutableMap<String, Any>? = null

    private val mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action ?: return
            when (action) {
                WifiManager.NETWORK_STATE_CHANGED_ACTION, LocationManager.PROVIDERS_CHANGED_ACTION -> mBroadcastData!!.setValue(
                    action
                )
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        context = this
        mCacheMap = HashMap()
        mBroadcastData = MutableLiveData()
        val filter = IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            filter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION)
        }
        registerReceiver(mReceiver, filter)
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterReceiver(mReceiver)
    }

    fun getInstance(): MyApp? {
        return app
    }

    fun observeBroadcast(owner: LifecycleOwner?, observer: Observer<String>?) {
        mBroadcastData!!.observe(owner!!, observer!!)
    }

    fun observeBroadcastForever(observer: Observer<String>?) {
        mBroadcastData!!.observeForever(observer!!)
    }

    fun removeBroadcastObserver(observer: Observer<String>?) {
        mBroadcastData!!.removeObserver(observer!!)
    }

    fun putCache(key: String, value: Any) {
        mCacheMap!![key] = value
    }

    fun takeCache(key: String): Any? {
        return mCacheMap!!.remove(key)
    }

    companion object{
        lateinit var context: Context
    }
}