package com.kiyosuke.corona_grapher.data.api.response

data class Timeline(
    val latest: Long,
    val timeline: Map<String, Long>
)