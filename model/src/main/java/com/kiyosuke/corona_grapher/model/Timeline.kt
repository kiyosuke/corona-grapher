package com.kiyosuke.corona_grapher.model

import org.threeten.bp.Instant

data class Timeline(
    val latest: Long,
    val timeline: Map<Instant, Long>
)