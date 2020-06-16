package com.khanhlh.firewarningkt

import android.content.Context

class ResourceProvider
constructor(private var context: Context) {
    fun getString(resId: Int) = context.getString(resId)
}