package com.kiyosuke.corona_grapher.data.repository.mapper

import com.kiyosuke.corona_grapher.common.atUTC
import com.kiyosuke.corona_grapher.model.Timeline
import com.kiyosuke.corona_grapher.model.Timelines
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import com.kiyosuke.corona_grapher.data.api.response.Timeline as ApiTimeline
import com.kiyosuke.corona_grapher.data.api.response.Timelines as ApiTimelines

fun ApiTimelines.toTimelines(): Timelines {
    return Timelines(
        confirmed = confirmed.toTimeline(),
        deaths = deaths.toTimeline(),
        recovered = recovered.toTimeline()
    )
}

fun ApiTimeline.toTimeline(): Timeline {
    return Timeline(latest = latest, timeline = timeline.mapKeys { parseDateString(it.key) })
}

private fun parseDateString(dateString: String): Instant =
    LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME).atUTC().toInstant()