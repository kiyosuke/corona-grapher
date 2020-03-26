package com.kiyosuke.corona_grapher.data.db

import androidx.room.Room
import com.kiyosuke.corona_grapher.data.db.internal.CacheDatabase
import com.kiyosuke.corona_grapher.data.db.internal.RoomDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

val dbModule = module {
    single {
        Room.databaseBuilder(androidContext(), CacheDatabase::class.java, "corona_grapher.db")
            .build()
    }
    dao()

    single { RoomDatabase(get(), get()) }

    single<CoronavirusDatabase> { get<RoomDatabase>() }
}

private fun Module.dao() {
    single { get<CacheDatabase>().locationDao() }
}