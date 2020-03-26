package com.kiyosuke.corona_grapher.model

data class Timelines(
    val confirmed: Timeline,
    val deaths: Timeline,
    val recovered: Timeline
)