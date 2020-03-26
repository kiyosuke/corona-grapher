package com.kiyosuke.corona_grapher

import android.app.Application
import com.kiyosuke.corona_grapher.data.api.apiModule
import com.kiyosuke.corona_grapher.data.db.dbModule
import com.kiyosuke.corona_grapher.data.repository.repoModule
import com.kiyosuke.corona_grapher.di.androidModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            modules(
                listOf(
                    apiModule,
                    dbModule,
                    repoModule,
                    androidModule
                )
            )
            androidContext(this@App)
        }
    }
}