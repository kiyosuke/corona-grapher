package com.kiyosuke.corona_grapher.data.memory

data class Timestamp(val millis: Long) {

    companion object {
        fun realtime() = Timestamp(System.currentTimeMillis())
    }
}