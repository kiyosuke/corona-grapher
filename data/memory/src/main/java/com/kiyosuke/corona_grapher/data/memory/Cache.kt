package com.kiyosuke.corona_grapher.data.memory

data class Cache<out T>(
    val timestamp: Timestamp,
    val value: T
) {
    fun isExpired(expiration: Expiration): Boolean {
        val realtime = Timestamp.realtime()
        return (realtime.millis - timestamp.millis) >= expiration.millis
    }
}