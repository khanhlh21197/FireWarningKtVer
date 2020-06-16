package com.khanhlh.firewarningkt.helper.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.khanhlh.firewarningkt.R
import jp.wasabeef.glide.transformations.CropCircleTransformation

/**
 * 页面描述：ImageUtil
 *
 * Created by ditclear on 2017/10/21.
 */

object ImageUtil {

    fun load(uri: String?, imageView: ImageView, isAvatar: Boolean = false) {
        if (!isAvatar) {
            Glide.with(imageView.context).load(uri)
                .placeholder(R.color.tools_color)
                .error(R.color.tools_color)
                .into(imageView)
        } else {
            Glide.with(imageView.context).load(uri)
                .bitmapTransform(CropCircleTransformation(imageView.context))
                .placeholder(R.drawable.ic_warning_red)
                .error(R.drawable.ic_warning_red)
                .into(imageView)
        }
    }

}