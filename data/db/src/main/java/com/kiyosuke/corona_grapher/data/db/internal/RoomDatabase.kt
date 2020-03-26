package com.kiyosuke.corona_grapher.data.db.internal

import androidx.room.withTransaction
import com.kiyosuke.corona_grapher.data.api.response.LocationsResponse
import com.kiyosuke.corona_grapher.data.db.CoronavirusDatabase
import com.kiyosuke.corona_grapher.data.db.entity.LocationEntity
import com.kiyosuke.corona_grapher.data.db.internal.dao.LocationDao
import com.kiyosuke.corona_grapher.data.db.internal.entity.mappter.toEntities
import kotlinx.coroutines.flow.Flow

internal class RoomDatabase(
    private val database: CacheDatabase,
    private val locationDao: LocationDao
): CoronavirusDatabase {

    override suspend fun saveLocations(apiResponse: LocationsResponse) {
        database.withTransaction {
            locationDao.deleteAll()
            val locations = apiResponse.locations.toEntities()
            locationDao.insert(locations)
        }
    }

    override fun locations(): Flow<List<LocationEntity>> = locationDao.locations()
}