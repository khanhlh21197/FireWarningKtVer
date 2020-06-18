package com.khanhlh.firewarningkt.helper.utils

fun Float.fToC(): Float = let { (it - 32) * 5 / 9 }

fun Float.cToF(): Float = let { 9 / 5 * it + 32 }