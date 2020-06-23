package com.khanhlh.firewarningkt.view.gallery

import androidx.lifecycle.MutableLiveData
import com.khanhlh.firewarningkt.helper.extens.init
import com.khanhlh.firewarningkt.helper.extens.set
import com.khanhlh.firewarningkt.viewmodel.BaseViewModel

class GalleryViewModel : BaseViewModel() {
    var progress = MutableLiveData<String>().init("")

    fun notifyProgressChanged(progress: Int) = this.progress.set("$progress")
}