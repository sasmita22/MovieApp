package com.hiroshisasmita.core.extension

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.extLoadImage(url: String, @DrawableRes placeholderId: Int? = null, options: RequestOptions = RequestOptions()) {
    Glide.with(this)
        .load(url)
        .apply(options).let {
            if (placeholderId != null) it.placeholder(placeholderId)
            else it
        }.into(this)
}