package com.kiyosuke.corona_grapher.common

import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

fun Instant.atUTC(): ZonedDateTime {
    return atZone(ZoneId.of("UTC"))
}

fun Instant.atSystemDefault(): ZonedDateTime {
    return atZone(ZoneId.systemDefault())
}