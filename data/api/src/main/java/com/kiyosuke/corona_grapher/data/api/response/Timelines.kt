package com.kiyosuke.corona_grapher.data.api.response

data class Timelines(
    val confirmed: Timeline,
    val deaths: Timeline,
    val recovered: Timeline
)