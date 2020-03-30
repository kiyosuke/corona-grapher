package dependencies

object Dep {

    object GradlePlugins {
        val android = "com.android.tools.build:gradle:3.5.3"
        val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"
        val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${AndroidX.Navigation.version}"
        val deploygate = "com.deploygate:gradle:2.1.0"
    }

    object AndroidX {
        val constraintLayout = "androidx.constraintlayout:constraintlayout:1.1.3"
        val recyclerView = "androidx.recyclerview:recyclerview:1.1.0"
        val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:1.1.0"
        val cardview = "androidx.cardview:cardview:1.0.0"
        val materialComponent = "com.google.android.material:material:1.1.0"
        val appcompat = "androidx.appcompat:appcompat:1.1.0"
        val coreKtx = "androidx.core:core-ktx:1.2.0"
        val fragmentKtx = "androidx.fragment:fragment-ktx:1.2.3"

        object Lifecycle {
            val version = "2.2.0"
            val extensions = "androidx.lifecycle:lifecycle-extensions:$version"
            val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            val livedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
        }

        object Room {
            val version = "2.2.5"
            val runtime = "androidx.room:room-runtime:$version"
            val ktx = "androidx.room:room-ktx:$version"
            val compiler = "androidx.room:room-compiler:$version"
        }

        object Navigation {
            val version = "2.2.1"
            val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:$version"
            val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
        }
    }

    object Kotlin {
        val version = "1.3.71"
        val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$version"

        object Coroutines {
            val version = "1.3.3"
            val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
        }
    }

    object Groupie {
        val version = "2.7.2"
        val core = "com.xwray:groupie:$version"
        val databinding = "com.xwray:groupie-databinding:$version"
    }

    object Koin {
        val version = "2.0.1"
        val core = "org.koin:koin-android:$version"
        val viewmodel = "org.koin:koin-android-viewmodel:$version"
    }

    object OkHttp {
        val version = "4.4.0"
        val core = "com.squareup.okhttp3:okhttp:$version"
        val logging = "com.squareup.okhttp3:logging-interceptor:$version"
    }

    object Retrofit {
        val version = "2.8.1"
        val core = "com.squareup.retrofit2:retrofit:$version"
        val moshi = "com.squareup.retrofit2:converter-moshi:$version"
    }

    object Moshi {
        val version = "1.9.2"
        val core = "com.squareup.moshi:moshi:$version"
        val kotlin = "com.squareup.moshi:moshi-kotlin:$version"
    }

    object GoogleMaps {
        val maps = "com.google.android.gms:play-services-maps:17.0.0"
        val utils = "com.google.maps.android:android-maps-utils:1.0.2"
    }

    object ThreeTenAbp {
        val core = "com.jakewharton.threetenabp:threetenabp:1.2.1"
    }

    object Timber {
        val core = "com.jakewharton.timber:timber:4.7.1"
    }

    object MPAndroidChart {
        val core = "com.github.PhilJay:MPAndroidChart:v3.1.0"
    }
}