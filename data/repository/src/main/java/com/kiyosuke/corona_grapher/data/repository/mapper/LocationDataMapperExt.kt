package com.kiyosuke.corona_grapher.data.repository.mapper

import com.kiyosuke.corona_grapher.data.db.entity.LocationEntity
import com.kiyosuke.corona_grapher.model.Coordinates
import com.kiyosuke.corona_grapher.model.Latest
import com.kiyosuke.corona_grapher.model.Location
import com.kiyosuke.corona_grapher.model.LocationId

fun LocationEntity.toLocation(): Location {
    return Location.List(
        id = LocationId(id),
        country = country,
        countryCode = countryCode,
        countryPopulation = countryPopulation,
        province = province,
        lastUpdated = lastUpdated,
        coordinates = Coordinates(latitude, longitude),
        latest = Latest(lastConfirmed, lastDeaths, lastRecovered)
    )
}

fun List<LocationEntity>.toLocations(): List<Location> = this.map { it.toLocation() }

fun com.kiyosuke.corona_grapher.data.api.response.Location.toLocation(): Location.Detail {
    return Location.Detail(
        id = LocationId(id),
        country = country,
        countryCode = country_code,
        countryPopulation = country_population,
        province = province,
        lastUpdated = last_updated,
        coordinates = coordinates.toCoordinates(),
        latest = latest.toLatest(),
        timelines = requireNotNull(timelines).toTimelines()
    )
}