package com.kiyosuke.corona_grapher.data.db.entity

import org.threeten.bp.Instant

interface LocationEntity {
    val id: Long
    val country: String
    val countryCode: String
    val countryPopulation: Long?
    val province: String
    val lastUpdated: Instant
    val latitude: Double
    val longitude: Double
    val lastConfirmed: Long
    val lastDeaths: Long
    val lastRecovered: Long
}