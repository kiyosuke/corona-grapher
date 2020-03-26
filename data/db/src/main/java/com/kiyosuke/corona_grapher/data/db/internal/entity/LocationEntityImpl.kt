package com.kiyosuke.corona_grapher.data.db.internal.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kiyosuke.corona_grapher.data.db.entity.LocationEntity
import org.threeten.bp.Instant

@Entity(tableName = "t_locations")
internal data class LocationEntityImpl(
    @PrimaryKey override val id: Long,
    override val country: String,
    override val countryCode: String,
    override val countryPopulation: Long?,
    override val province: String,
    override val lastUpdated: Instant,
    override val latitude: Double,
    override val longitude: Double,
    override val lastConfirmed: Long,
    override val lastDeaths: Long,
    override val lastRecovered: Long
) : LocationEntity