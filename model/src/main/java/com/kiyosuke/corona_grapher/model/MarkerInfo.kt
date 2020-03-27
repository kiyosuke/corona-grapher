package com.kiyosuke.corona_grapher.model

data class MarkerInfo(
    val locationName: String,
    val confirmed: Long,
    val deaths: Long,
    val recovered: Long,
    val hasDescription: Boolean
)