package com.khanhlh.firewarningkt.esptouch

import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView

internal class EspTouchViewModel {
    var apSsidTV: TextView? = null
    var apBssidTV: TextView? = null
    var apPasswordEdit: EditText? = null
    var deviceCountEdit: EditText? = null
    var packageModeGroup: RadioGroup? = null
    var messageView: TextView? = null
    var confirmBtn: Button? = null
    var ssid: String? = null
    var ssidBytes: ByteArray? = null
    var bssid: String? = null
    var message: CharSequence? = null
    var confirmEnable = false
    fun invalidateAll() {
        apSsidTV!!.text = ssid
        apBssidTV!!.text = bssid
        messageView!!.text = message
        confirmBtn!!.isEnabled = confirmEnable
    }
}