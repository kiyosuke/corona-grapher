package com.kiyosuke.corona_grapher.data.repository.impl

import com.kiyosuke.corona_grapher.data.api.CoronavirusTrackerApi
import com.kiyosuke.corona_grapher.data.db.CoronavirusDatabase
import com.kiyosuke.corona_grapher.data.repository.CoronavirusRepository
import com.kiyosuke.corona_grapher.data.repository.mapper.toLatest
import com.kiyosuke.corona_grapher.data.repository.mapper.toLocation
import com.kiyosuke.corona_grapher.data.repository.mapper.toLocations
import com.kiyosuke.corona_grapher.model.Latest
import com.kiyosuke.corona_grapher.model.Location
import com.kiyosuke.corona_grapher.model.LocationId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class CoronavirusDataRepository(
    private val api: CoronavirusTrackerApi,
    private val database: CoronavirusDatabase
) : CoronavirusRepository {

    override suspend fun refreshLocations() {
        val locations = api.allLocations()
        database.saveLocations(locations)
    }

    override fun locations(): Flow<List<Location>> {
        return database.locations().map { entities -> entities.toLocations() }
    }

    // TODO: メモリキャッシュ
    override suspend fun location(id: LocationId): Location.Detail {
        return api.location(id.id).location.toLocation()
    }

    override suspend fun latest(): Latest {
        return api.latest().latest.toLatest()
    }
}