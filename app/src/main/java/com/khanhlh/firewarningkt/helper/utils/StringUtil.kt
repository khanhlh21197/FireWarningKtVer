package com.khanhlh.firewarningkt.helper.utils

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
fun String.upperFirstLetter() =
    let { it.substring(0, 1).toUpperCase() + it.substring(1, it.length) }