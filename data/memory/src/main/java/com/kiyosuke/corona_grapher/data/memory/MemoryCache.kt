package com.kiyosuke.corona_grapher.data.memory

import com.kiyosuke.corona_grapher.data.memory.internal.MemoryCacheBuilderImpl

interface MemoryCache<K, V> {

    fun get(key: K): Cache<V>?

    fun put(key: K, value: V)

    fun remove(key: K)

    fun evictAll()

    interface Builder<K, V> {
        fun setExpireTime(expiration: Expiration): Builder<K, V>
        fun setMaxSize(maxSize: Int): Builder<K, V>
        fun build(): MemoryCache<K, V>
    }

    companion object {
        fun <K, V> builder(): Builder<K, V> = MemoryCacheBuilderImpl()
    }
}