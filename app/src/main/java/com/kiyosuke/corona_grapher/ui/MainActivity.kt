package com.kiyosuke.corona_grapher.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kiyosuke.corona_grapher.R
import com.kiyosuke.corona_grapher.databinding.ActivityMainBinding
import com.kiyosuke.corona_grapher.model.Location
import com.kiyosuke.corona_grapher.util.ext.dataBinding
import com.kiyosuke.corona_grapher.util.ext.observeNonNull
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModel()
    private val binding: ActivityMainBinding by dataBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.locations.observeNonNull(this) { locations ->

        }

        viewModel.locationLoadState.observeNonNull(this) { state ->
            Timber.d("state=$state")
        }

        Timber.d("onCreate")

        viewModel.refresh()
    }

    private fun requestLocationDetail(location: Location) {
        viewModel.requestDetail(location.id)
    }
}