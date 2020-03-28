package com.kiyosuke.corona_grapher.data.api.internal

import com.kiyosuke.corona_grapher.data.api.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.Instant
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

internal object ApiClient {
    val okHttpClient: OkHttpClient

    val moshi = Moshi.Builder()
        .add(Instant::class.java, InstantAdapter())
        .add(KotlinJsonAdapterFactory())
        .build()

    val retrofit: Retrofit

    private val moshiConverterFactory: Converter.Factory

    init {
        val clientBuilder = OkHttpClient().newBuilder().apply {
            readTimeout(20 * 1000, TimeUnit.MILLISECONDS)
            writeTimeout(30 * 1000, TimeUnit.MILLISECONDS)
            connectTimeout(30 * 1000, TimeUnit.MILLISECONDS)
            followRedirects(false)

            val logging = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
            addInterceptor(logging)
        }
        okHttpClient = clientBuilder.build()

        moshiConverterFactory = MoshiConverterFactory.create(
            moshi
        )

        val builder = Retrofit.Builder()
            .baseUrl(BuildConfig.API_ENDPOINT)
            .addConverterFactory(moshiConverterFactory)
            .client(okHttpClient)

        retrofit = builder.build()
    }

    inline fun <reified S> create(): S = retrofit.create()
}