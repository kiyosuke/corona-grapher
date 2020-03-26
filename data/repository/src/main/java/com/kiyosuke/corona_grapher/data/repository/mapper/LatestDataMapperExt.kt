package com.kiyosuke.corona_grapher.data.repository.mapper

import com.kiyosuke.corona_grapher.model.Latest
import com.kiyosuke.corona_grapher.data.api.response.Latest as ApiLatest

fun ApiLatest.toLatest(): Latest {
    return Latest(confirmed, deaths, recovered)
}