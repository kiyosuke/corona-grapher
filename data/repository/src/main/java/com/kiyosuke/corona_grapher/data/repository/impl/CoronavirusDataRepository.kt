package com.kiyosuke.corona_grapher.data.repository.impl

import com.kiyosuke.corona_grapher.data.api.CoronavirusTrackerApi
import com.kiyosuke.corona_grapher.data.db.CoronavirusDatabase
import com.kiyosuke.corona_grapher.data.memory.MemoryCache
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
    private val database: CoronavirusDatabase,
    private val cache: MemoryCache<LocationId, Location.Detail>
) : CoronavirusRepository {

    override suspend fun refreshLocations() {
        val locations = api.allLocations()
        database.saveLocations(locations)
    }

    override fun locations(): Flow<List<Location>> {
        return database.locations().map { entities -> entities.toLocations() }
    }

    override suspend fun location(id: LocationId): Location.Detail {
        // メモリキャッシュが有効なら利用
        val memCache = cache.get(id)?.value
        if (memCache != null) {
            return memCache
        }
        val data = api.location(id.id).location.toLocation()
        cache.put(id, data)
        return data
    }

    override suspend fun latest(): Latest {
        return api.latest().latest.toLatest()
    }
}