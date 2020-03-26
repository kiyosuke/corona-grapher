package com.kiyosuke.corona_grapher.data.api

import com.kiyosuke.corona_grapher.data.api.internal.ApiClient
import org.koin.dsl.module

val apiModule = module {
    single<CoronavirusTrackerApi> { ApiClient.create() }
}