package com.kiyosuke.corona_grapher.ui.map

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.kiyosuke.corona_grapher.R
import com.kiyosuke.corona_grapher.databinding.MapFragmentBinding
import com.kiyosuke.corona_grapher.model.Location
import com.kiyosuke.corona_grapher.util.ext.dataBinding
import com.kiyosuke.corona_grapher.util.ext.observeNonNull
import com.kiyosuke.corona_grapher.util.ext.toLatLng
import org.koin.android.viewmodel.ext.android.viewModel

class MapFragment : Fragment(R.layout.map_fragment), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private val viewModel: MapViewModel by viewModel()
    private val binding: MapFragmentBinding by dataBinding()

    private var googleMap: GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.locations.observeNonNull(viewLifecycleOwner) { locations ->
            updateLocations(locations)
        }

        viewModel.message.observeNonNull(viewLifecycleOwner) { message ->

        }

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.refresh()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        this.googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(40.0, 140.0), 16f))

        this.googleMap?.setOnMarkerClickListener(this)
    }

    private fun updateLocations(locations: List<Location>) {
        this.googleMap?.clear()
        // TODO: 感染者数をマーカーに表示させるためのアイコンを用意（数値を描画できるDrawable?）
        // TODO: 感染者数に応じてマーカーアイコンの大きさと色を変更する
        locations.forEach { location ->
            val options = createMarkerOptions(location)
            val marker = this.googleMap?.addMarker(options)
            marker?.tag = location
        }
    }

    private fun createMarkerOptions(location: Location): MarkerOptions {
        return MarkerOptions().apply {
            position(location.coordinates.toLatLng())
            title(location.country)
            snippet(location.countryCode)
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        // TODO: マーカーに紐づくLocationの詳細画面Fragmentをボトムシートに表示する
        // TODO: ボトムシートのpeekHeight部分に国名と感染者数、死亡者数の数値を表示する
        viewModel.onClickedMarker(marker)
        return false
    }
}
