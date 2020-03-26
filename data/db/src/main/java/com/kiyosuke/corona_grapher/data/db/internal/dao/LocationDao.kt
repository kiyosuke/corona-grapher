package com.kiyosuke.corona_grapher.data.db.internal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kiyosuke.corona_grapher.data.db.internal.entity.LocationEntityImpl
import kotlinx.coroutines.flow.Flow

@Dao
internal interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(locations: List<LocationEntityImpl>)

    @Query("SELECT * FROM t_locations")
    fun locations(): Flow<List<LocationEntityImpl>>

    @Query("DELETE FROM t_locations")
    suspend fun deleteAll()
}