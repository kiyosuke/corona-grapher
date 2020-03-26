package com.kiyosuke.corona_grapher.data.repository.mapper

import com.kiyosuke.corona_grapher.model.Coordinates
import com.kiyosuke.corona_grapher.data.api.response.Coordinates as ApiCoordinates

fun ApiCoordinates.toCoordinates(): Coordinates {
    return Coordinates(latitude.toDouble(), longitude.toDouble())
}