package com.kiyosuke.corona_grapher.data.api.response

data class LocationsResponse(
    val latest: Latest,
    val locations: List<Location>
)