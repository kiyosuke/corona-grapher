package com.kiyosuke.corona_grapher.data.memory.internal

import com.kiyosuke.corona_grapher.data.memory.Expiration
import com.kiyosuke.corona_grapher.data.memory.MemoryCache

internal class MemoryCacheBuilderImpl<K, V> : MemoryCache.Builder<K, V> {
    private var expiration = Expiration.bySeconds(300)
    private var maxSize = 10

    override fun setExpireTime(expiration: Expiration): MemoryCache.Builder<K, V> = apply {
        this.expiration = expiration
    }

    override fun setMaxSize(maxSize: Int): MemoryCache.Builder<K, V> = apply {
        check(maxSize > 0) { "キャッシュ最大サイズは1以上を指定してください" }
        this.maxSize = maxSize
    }

    override fun build(): MemoryCache<K, V> {
        return MemoryCacheImpl(expiration, maxSize)
    }
}