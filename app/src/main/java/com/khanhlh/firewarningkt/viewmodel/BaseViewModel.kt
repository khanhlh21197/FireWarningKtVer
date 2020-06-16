package com.khanhlh.firewarningkt.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    val loading = ObservableBoolean(false)

    val loadMore = ObservableBoolean(false)

    val empty = ObservableBoolean(false)

    val blurring = ObservableBoolean(false)

    fun startLoad() {
        loading.set(true)
    }

    fun stopLoad() {
        loading.set(false)
    }

    fun startBlur() {
        blurring.set(true)
    }

    fun stopBlur() {
        blurring.set(false)
    }
}