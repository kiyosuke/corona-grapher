package com.kiyosuke.corona_grapher.data.memory

import java.util.concurrent.TimeUnit

class Expiration private constructor(val millis: Long) {

    val seconds: Long get() = TimeUnit.MILLISECONDS.toSeconds(millis)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Expiration

        if (millis != other.millis) return false

        return true
    }

    override fun hashCode(): Int {
        return millis.hashCode()
    }

    companion object {
        fun byMillis(mills: Long) = Expiration(mills)
        fun bySeconds(seconds: Long) = Expiration(TimeUnit.SECONDS.toMillis(seconds))
    }
}