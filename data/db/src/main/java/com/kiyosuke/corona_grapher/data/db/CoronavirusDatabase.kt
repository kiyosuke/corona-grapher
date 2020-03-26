package com.kiyosuke.corona_grapher.data.db

import com.kiyosuke.corona_grapher.data.api.response.LocationsResponse
import com.kiyosuke.corona_grapher.data.db.entity.LocationEntity
import kotlinx.coroutines.flow.Flow

interface CoronavirusDatabase {

    suspend fun saveLocations(apiResponse: LocationsResponse)

    fun locations(): Flow<List<LocationEntity>>
}