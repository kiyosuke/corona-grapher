package com.kiyosuke.corona_grapher.data.repository

import com.kiyosuke.corona_grapher.data.memory.Expiration
import com.kiyosuke.corona_grapher.data.memory.MemoryCache
import com.kiyosuke.corona_grapher.data.repository.impl.CoronavirusDataRepository
import com.kiyosuke.corona_grapher.model.Latest
import com.kiyosuke.corona_grapher.model.Location
import com.kiyosuke.corona_grapher.model.LocationId
import org.koin.dsl.module

val repoModule = module {
    single<CoronavirusRepository> {
        val locationCache = MemoryCache.builder<LocationId, Location.Detail>()
            .setExpireTime(Expiration.bySeconds(300))
            .setMaxSize(20)
            .build()
        val latestCache = MemoryCache.builder<String, Latest>()
            .setExpireTime(Expiration.bySeconds(300))
            .setMaxSize(1)
            .build()
        CoronavirusDataRepository(get(), get(), locationCache, latestCache)
    }
}