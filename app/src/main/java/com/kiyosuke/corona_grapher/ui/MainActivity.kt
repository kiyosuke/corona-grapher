package com.kiyosuke.corona_grapher.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kiyosuke.corona_grapher.R
import timber.log.Timber

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.d("onCreate")
    }

}