package com.kiyosuke.corona_grapher.data.db.internal

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kiyosuke.corona_grapher.data.db.internal.adapter.InstantConverter
import com.kiyosuke.corona_grapher.data.db.internal.dao.LocationDao
import com.kiyosuke.corona_grapher.data.db.internal.entity.LocationEntityImpl

@Database(
    entities = [
        LocationEntityImpl::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(InstantConverter::class)
internal abstract class CacheDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}