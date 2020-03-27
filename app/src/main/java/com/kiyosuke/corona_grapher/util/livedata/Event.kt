package com.kiyosuke.corona_grapher.util.livedata

data class Event<out T>(private val content: T) {
    var hasBeenHandled: Boolean = false
        private set

    fun getContentIfNotHandled(): T? =
        if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }

    fun peekContent(): T = content
}