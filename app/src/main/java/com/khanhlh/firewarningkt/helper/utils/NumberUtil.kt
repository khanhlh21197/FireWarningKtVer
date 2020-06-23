package com.khanhlh.firewarningkt.helper.utils

import java.text.DecimalFormat

fun Float.oneDigit(): String = let { DecimalFormat("#.#").format(it) }