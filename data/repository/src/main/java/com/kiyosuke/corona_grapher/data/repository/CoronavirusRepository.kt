package com.kiyosuke.corona_grapher.data.repository

import com.kiyosuke.corona_grapher.model.Latest
import com.kiyosuke.corona_grapher.model.Location
import com.kiyosuke.corona_grapher.model.LocationId
import kotlinx.coroutines.flow.Flow

interface CoronavirusRepository {

    suspend fun refreshLocations()

    fun locations(): Flow<List<Location>>

    suspend fun location(id: LocationId): Location.Detail

    suspend fun latest(): Latest
}