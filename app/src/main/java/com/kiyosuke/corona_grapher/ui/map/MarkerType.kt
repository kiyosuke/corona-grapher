package com.kiyosuke.corona_grapher.ui.map

import androidx.annotation.StringRes
import com.kiyosuke.corona_grapher.R

enum class MarkerType(@StringRes val labelResId: Int) {
    CONFIRMED(R.string.confirmed_count),
    DEATHS(R.string.deaths_count),
    RECOVERED(R.string.recovered_count)
}