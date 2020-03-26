package com.kiyosuke.corona_grapher.ui.locations.detail

import android.os.Bundle
import androidx.core.os.bundleOf
import com.kiyosuke.corona_grapher.model.LocationId

data class LocationDetailArgs(
    val locationId: LocationId
) {

    fun toBundle(): Bundle {
        return bundleOf(KEY_LOCATION_ID to locationId)
    }

    companion object {
        private const val KEY_LOCATION_ID =
            "com.kiyosuke.corona_grapher.ui.map.LocationDetailArgs.KEY_LOCATION_ID"

        fun fromBundle(bundle: Bundle): LocationDetailArgs {
            val locationId = requireNotNull(bundle.getParcelable<LocationId>(KEY_LOCATION_ID)) {
                "LocationId is null"
            }

            return LocationDetailArgs(locationId)
        }
    }
}