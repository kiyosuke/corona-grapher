package com.kiyosuke.corona_grapher.common

import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

fun LocalDateTime.atUTC(): ZonedDateTime {
    return atZone(ZoneId.of("UTC"))
}

fun LocalDateTime.atSystemDefault(): ZonedDateTime {
    return atZone(ZoneId.systemDefault())
}