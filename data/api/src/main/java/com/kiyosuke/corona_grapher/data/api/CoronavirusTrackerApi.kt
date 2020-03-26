package com.kiyosuke.corona_grapher.data.api

import com.kiyosuke.corona_grapher.data.api.response.LatestResponse
import com.kiyosuke.corona_grapher.data.api.response.LocationResponse
import com.kiyosuke.corona_grapher.data.api.response.LocationsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CoronavirusTrackerApi {

    @GET("locations")
    suspend fun allLocations(): LocationsResponse

    @GET("latest")
    suspend fun latest(): LatestResponse

    @GET("locations/{id}")
    suspend fun location(@Path("id") id: Long): LocationResponse
}