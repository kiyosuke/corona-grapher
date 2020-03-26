package com.kiyosuke.corona_grapher.model

sealed class LoadState<out T> {
    object Loading : LoadState<Nothing>()
    data class Loaded<T>(val value: T) : LoadState<T>()
    data class Error(val throwable: Throwable) : LoadState<Nothing>()
}

val <T> LoadState<T>.isLoading: Boolean get() = this is LoadState.Loading

fun <T> LoadState<T>.getValueOrNull(): T? = (this as? LoadState.Loaded)?.value

fun <T> LoadState<T>.getErrorOrNull(): Throwable? = (this as? LoadState.Error)?.throwable