package com.khanhlh.firewarningkt.helper.utils

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.khanhlh.firewarningkt.constant.Constants
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Date.toHour(): String = let { SimpleDateFormat(Constants.JUST_HOUR).format(it) }

fun Long.unixToDate() = Date(this.times(1000))

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
fun Any.currentTime(): String = SimpleDateFormat(Constants.DATE_AND_HOUR).format(LocalDateTime.now())