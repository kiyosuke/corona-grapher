package com.kiyosuke.corona_grapher.data.repository

import com.kiyosuke.corona_grapher.data.repository.impl.CoronavirusDataRepository
import org.koin.dsl.module

val repoModule = module {
    single<CoronavirusRepository> { CoronavirusDataRepository(get(), get()) }
}