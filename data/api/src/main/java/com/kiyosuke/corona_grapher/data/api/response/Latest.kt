package com.kiyosuke.corona_grapher.data.api.response

data class Latest(
    val confirmed: Long,
    val deaths: Long,
    val recovered: Long
)