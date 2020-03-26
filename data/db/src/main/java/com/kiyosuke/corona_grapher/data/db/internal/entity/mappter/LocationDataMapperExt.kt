package com.kiyosuke.corona_grapher.data.db.internal.entity.mappter

import com.kiyosuke.corona_grapher.data.api.response.Location
import com.kiyosuke.corona_grapher.data.db.internal.entity.LocationEntityImpl

internal fun List<Location>.toEntities(): List<LocationEntityImpl> {
    return map { location ->
        LocationEntityImpl(
            id = location.id,
            country = location.country,
            countryCode = location.country_code,
            countryPopulation = location.country_population,
            province = location.province,
            lastUpdated = location.last_updated,
            latitude = location.coordinates.latitude.toDouble(),
            longitude = location.coordinates.longitude.toDouble(),
            lastConfirmed = location.latest.confirmed,
            lastDeaths = location.latest.deaths,
            lastRecovered = location.latest.recovered
        )
    }
}