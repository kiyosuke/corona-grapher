package com.kiyosuke.corona_grapher.view

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("goneUnless")
fun View.goneUnless(isVisible: Boolean) {
    this.isVisible = isVisible
}