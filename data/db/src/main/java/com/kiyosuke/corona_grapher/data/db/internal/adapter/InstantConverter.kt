package com.kiyosuke.corona_grapher.data.db.internal.adapter

import androidx.room.TypeConverter
import com.kiyosuke.corona_grapher.common.atUTC
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

class InstantConverter {

    @TypeConverter
    fun from(value: String?): Instant? {
        return if (value == null) {
            null
        } else {
            parseDateString(value)
        }
    }

    @TypeConverter
    fun to(value: Instant?): String? {
        return value?.atUTC()?.format(DateTimeFormatter.ISO_DATE_TIME)
    }

    companion object {
        private fun parseDateString(dateString: String): Instant {
            return LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME).atUTC()
                .toInstant()
        }
    }
}