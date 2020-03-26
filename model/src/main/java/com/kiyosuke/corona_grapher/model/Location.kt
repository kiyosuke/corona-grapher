package com.kiyosuke.corona_grapher.model

import org.threeten.bp.Instant

sealed class Location(
    open val id: LocationId,
    open val country: String,
    open val countryCode: String,
    open val countryPopulation: Long?,
    open val province: String,
    open val lastUpdated: Instant,
    open val coordinates: Coordinates,
    open val latest: Latest
) {

    data class List(
        override val id: LocationId,
        override val country: String,
        override val countryCode: String,
        override val countryPopulation: Long?,
        override val province: String,
        override val lastUpdated: Instant,
        override val coordinates: Coordinates,
        override val latest: Latest
    ) : Location(id, country, countryCode, countryPopulation, province, lastUpdated, coordinates, latest)

    data class Detail(
        override val id: LocationId,
        override val country: String,
        override val countryCode: String,
        override val countryPopulation: Long?,
        override val province: String,
        override val lastUpdated: Instant,
        override val coordinates: Coordinates,
        override val latest: Latest,
        val timelines: Timelines
    ) : Location(id, country, countryCode, countryPopulation, province, lastUpdated, coordinates, latest)
}