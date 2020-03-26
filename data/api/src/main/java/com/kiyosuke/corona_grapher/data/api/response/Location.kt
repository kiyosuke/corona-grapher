package com.kiyosuke.corona_grapher.data.api.response

import org.threeten.bp.Instant

data class Location(
    val id: Long,
    val country: String,
    val country_code: String,
    val country_population: Long?,
    val province: String,
    val last_updated: Instant,
    val coordinates: Coordinates,
    val latest: Latest,
    val timelines: Timelines?
)