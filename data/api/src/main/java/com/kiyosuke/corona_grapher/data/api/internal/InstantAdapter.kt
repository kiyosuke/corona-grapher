package com.kiyosuke.corona_grapher.data.api.internal

import com.kiyosuke.corona_grapher.common.atUTC
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

internal class InstantAdapter : JsonAdapter<Instant>() {
    override fun toJson(writer: JsonWriter, value: Instant?) {
        if (value == null) {
            writer.nullValue()
        } else {
            writer.value(value.atUTC().format(DateTimeFormatter.ISO_DATE_TIME))
        }
    }

    override fun fromJson(reader: JsonReader): Instant? = when (reader.peek()) {
        JsonReader.Token.NULL -> reader.nextNull()
        else -> {
            val dateString = reader.nextString()
            parseDateString(dateString)
        }
    }

    companion object {
        private fun parseDateString(dateString: String): Instant =
            LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME).atUTC().toInstant()
    }
}