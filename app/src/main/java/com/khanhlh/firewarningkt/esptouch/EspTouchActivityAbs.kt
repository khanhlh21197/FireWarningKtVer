package com.khanhlh.firewarningkt.esptouch

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.location.LocationManagerCompat
import com.khanhlh.firewarningkt.R
import com.khanhlh.firewarningkt.helper.extens.openActivity
import java.net.InetAddress

abstract class EspTouchActivityAbs : AppCompatActivity() {
    private var mWifiManager: WifiManager? = null
    protected abstract val espTouchVersion: String

    protected class StateResult {
        var message: CharSequence? = null
        var permissionGranted = false
        var locationRequirement = false
        var wifiConnected = false
        var is5G = false
        var address: InetAddress? = null
        var ssid: String? = null
        var ssidBytes: ByteArray? = null
        var bssid: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mWifiManager =
            applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }

    private fun showAboutDialog() {
        val esptouchVerText = espTouchVersion
        var appVer = ""
        val packageManager = packageManager
        try {
            val info = packageManager.getPackageInfo(packageName, 0)
            appVer = info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        val items = arrayOf<CharSequence>(
            getString(R.string.about_app_version, appVer),
            esptouchVerText
        )
        AlertDialog.Builder(this)
            .setTitle(R.string.menu_item_about)
            .setIcon(R.drawable.baseline_info_black_24)
            .setItems(items, null)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add(
            Menu.NONE,
            MENU_ITEM_ABOUT,
            0,
            R.string.menu_item_about
        )
            .setIcon(R.drawable.ic_info_outline_white_24dp)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                onBackPressed()
                return true
            }
            MENU_ITEM_ABOUT -> {
                showAboutDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    protected fun checkPermission(): StateResult {
        val result = StateResult()
        result.permissionGranted = false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val locationGranted =
                (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED)
            if (!locationGranted) {
                val splits =
                    getString(R.string.esptouch_message_permission).split("\n").toTypedArray()
                require(splits.size == 2) { "Invalid String @RES esptouch_message_permission" }
                val ssb = SpannableStringBuilder(splits[0])
                ssb.append('\n')
                val clickMsg = SpannableString(splits[1])
                val clickSpan = ForegroundColorSpan(-0xffdd01)
                clickMsg.setSpan(clickSpan, 0, clickMsg.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
                ssb.append(clickMsg)
                result.message = ssb
                return result
            }
        }
        result.permissionGranted = true
        return result
    }

    protected fun checkLocation(): StateResult {
        val result = StateResult()
        result.locationRequirement = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val manager =
                getSystemService(LocationManager::class.java)
            val enable =
                manager != null && LocationManagerCompat.isLocationEnabled(manager)
            if (!enable) {
                result.message = getString(R.string.esptouch_message_location)
                return result
            }
        }
        result.locationRequirement = false
        return result
    }

    protected fun checkWifi(): StateResult {
        val result = StateResult()
        result.wifiConnected = false
        val wifiInfo = mWifiManager!!.connectionInfo
        val connected = NetUtils.isWifiConnected(mWifiManager!!)
        if (!connected) {
            result.message = getString(R.string.esptouch_message_wifi_connection)
            return result
        }
        val ssid = NetUtils.getSsidString(wifiInfo)
        val ipValue = wifiInfo.ipAddress
        if (ipValue != 0) {
            result.address = NetUtils.getAddress(wifiInfo.ipAddress)
        } else {
            result.address = NetUtils.iPv4Address
            if (result.address == null) {
                result.address = NetUtils.iPv6Address
            }
        }
        result.wifiConnected = true
        result.message = ""
        result.is5G = NetUtils.is5G(wifiInfo.frequency)
        if (result.is5G) {
            result.message = getString(R.string.esptouch_message_wifi_frequency)
        }
        result.ssid = ssid
        result.ssidBytes = NetUtils.getRawSsidBytesOrElse(wifiInfo, ssid.toByteArray())
        result.bssid = wifiInfo.bssid
        return result
    }

    companion object {
        private const val MENU_ITEM_ABOUT = 0
    }
}