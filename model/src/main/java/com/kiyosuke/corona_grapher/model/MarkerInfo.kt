package com.kiyosuke.corona_grapher.model

import org.threeten.bp.Instant

data class MarkerInfo(
    val locationName: String,
    val confirmed: Long,
    val deaths: Long,
    val recovered: Long,
    val lastUpdated: Instant?,
    val hasDescription: Boolean
)