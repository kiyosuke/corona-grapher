package com.kiyosuke.corona_grapher.data.memory.internal

import android.util.LruCache
import com.kiyosuke.corona_grapher.data.memory.Cache
import com.kiyosuke.corona_grapher.data.memory.MemoryCache
import com.kiyosuke.corona_grapher.data.memory.Expiration
import com.kiyosuke.corona_grapher.data.memory.Timestamp

class MemoryCacheImpl<K, V>(
    private val expiration: Expiration,
    private val maxSize: Int
) : MemoryCache<K, V> {

    private val cache: LruCache<K, Cache<V>> = LruCache<K, Cache<V>>(maxSize)

    override fun get(key: K): Cache<V>? {
        val value = cache.get(key)
        if (value != null && value.isExpired(expiration)) {
            remove(key)
            return null
        }
        return value
    }

    override fun put(key: K, value: V) {
        cache.put(key, Cache(Timestamp.realtime(), value))
    }

    override fun remove(key: K) {
        cache.remove(key)
    }

    override fun evictAll() {
        cache.evictAll()
    }
}