package com.kiyosuke.corona_grapher.ui.map

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.drawToBitmap
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kiyosuke.corona_grapher.R
import com.kiyosuke.corona_grapher.common.atUTC
import com.kiyosuke.corona_grapher.databinding.CircleMarkerLayoutBinding
import com.kiyosuke.corona_grapher.databinding.MapFragmentBinding
import com.kiyosuke.corona_grapher.model.*
import com.kiyosuke.corona_grapher.util.color.Color
import com.kiyosuke.corona_grapher.util.ext.dataBinding
import com.kiyosuke.corona_grapher.util.ext.observeNonNull
import com.kiyosuke.corona_grapher.util.ext.toLatLng
import org.koin.android.viewmodel.ext.android.viewModel
import org.threeten.bp.format.DateTimeFormatter
import kotlin.math.abs
import kotlin.math.max

class MapFragment : Fragment(R.layout.map_fragment), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {

    private val viewModel: MapViewModel by viewModel()
    private val binding: MapFragmentBinding by dataBinding()

    private var googleMap: GoogleMap? = null

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    private val circleView: CircleMarkerLayoutBinding by lazy {
        CircleMarkerLayoutBinding.inflate(layoutInflater, null, false)
    }

    private var defaultGoogleLogoMargin = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = requireMapFragment()
        mapFragment.getMapAsync(this)

        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)

        val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(sheet: View, offset: Float) {
                if (offset > 0f) return
                val factor = (1 - abs(offset)).let {
                    if (it.isNaN()) 1f else it
                }

                val margin = max((bottomSheetBehavior.peekHeight * factor).toInt(), 0)
                findGoogleLogo()?.let { logo ->
                    if (defaultGoogleLogoMargin == 0) {
                        defaultGoogleLogoMargin = logo.marginBottom
                    }
                    logo.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                        bottomMargin = margin + defaultGoogleLogoMargin
                    }
                }
            }

            override fun onStateChanged(sheet: View, state: Int) {
                // do nothing
            }
        }

        bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback)

        viewModel.locations.observeNonNull(viewLifecycleOwner) { locations ->
            updateLocations(locations)
        }

        viewModel.bottomSheetState.observeNonNull(viewLifecycleOwner) { event ->
            val state = event.getContentIfNotHandled() ?: return@observeNonNull
            bottomSheetBehavior.state = state
        }

        viewModel.sheetInfo.observeNonNull(viewLifecycleOwner) { location ->
            updateSheetInfo(location)
        }

        viewModel.locationDetail.observeNonNull(viewLifecycleOwner) { state ->
            updateSheetTimelineChart(state)
        }

        viewModel.message.observeNonNull(viewLifecycleOwner) { message ->

        }

        viewModel.refresh()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        this.googleMap?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(40.0, 140.0)))

        this.googleMap?.setOnMarkerClickListener(this)
    }

    private fun requireMapFragment(): SupportMapFragment {
        return childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
    }

    private fun findGoogleLogo(): ImageView? {
        return requireMapFragment().view?.findViewWithTag("GoogleWatermark")
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
        val icon = createMarkerIcon(location.latest.confirmed)
        return MarkerOptions().apply {
            position(location.coordinates.toLatLng())
            title(location.country)
            snippet(location.countryCode)
            icon(BitmapDescriptorFactory.fromBitmap(icon))
        }
    }

    private fun createMarkerIcon(confirmedCount: Long): Bitmap {
        // FIXME: 一つの色をベースに明るさを変えて描画しているけど見た目が微妙。。
        val baseColor = Color(ContextCompat.getColor(requireContext(), R.color.danger))
        circleView.textCount.text = confirmedCount.toString()
        val color = when {
            confirmedCount >= 10000 -> baseColor.darker
            confirmedCount >= 1000 -> baseColor
            confirmedCount >= 100 -> baseColor.brighter
            else -> baseColor.brighter.brighter
        }
        circleView.circleFrame.circleColor = color.color

        // Bitmapに変換するために描画する必要あり
        circleView.circleFrame.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        circleView.circleFrame.layout(0, 0, circleView.circleFrame.measuredWidth, circleView.circleFrame.measuredHeight)

        return circleView.circleFrame.drawToBitmap()
    }

    private fun updateSheetInfo(location: Location) {
        binding.textCountry.text = location.countryFullName
        binding.textConfirmedCount.text = location.latest.confirmed.toString()
        binding.textDeathsCount.text = location.latest.deaths.toString()
        binding.textRecoveredCount.text = location.latest.recovered.toString()
    }

    private fun updateSheetTimelineChart(state: LoadState<Location.Detail>) {
        if (state.isLoading) {
            binding.timelineChart.clear()
        }
        binding.detailProgressbar.isVisible = state.isLoading
        binding.timelineChart.isVisible = state.isLoading.not()

        val timelines = state.getValueOrNull()?.timelines ?: return
        val confirmedSet = LineDataSet(createEntries(timelines.confirmed), getString(R.string.confirmed_count)).apply {
            this.color = ContextCompat.getColor(requireContext(), R.color.confirmed)
            setDrawCircles(false)
        }
        val deathsSet = LineDataSet(createEntries(timelines.deaths), getString(R.string.deaths_count)).apply {
            this.color = ContextCompat.getColor(requireContext(), R.color.deaths)
            setDrawCircles(false)
        }
        val recoveredSet = LineDataSet(createEntries(timelines.recovered), getString(R.string.recovered_count)).apply {
            this.color = ContextCompat.getColor(requireContext(), R.color.recovered)
            setDrawCircles(false)
        }
        val lineData = LineData(confirmedSet, deathsSet, recoveredSet)

        // X軸の日付生成
        val dates = timelines.confirmed.timeline.keys.map { instant ->
            instant.atUTC().format(DateTimeFormatter.ofPattern("MM/dd"))
        }
        binding.timelineChart.xAxis.valueFormatter = IndexAxisValueFormatter(dates)

        binding.timelineChart.description = null
        binding.timelineChart.setPinchZoom(true)
        binding.timelineChart.setScaleEnabled(true)
        binding.timelineChart.data = lineData
        binding.timelineChart.invalidate()
    }

    private fun createEntries(timeline: Timeline): List<Entry> {
        val result = mutableListOf<Entry>()
        timeline.timeline.values.forEachIndexed { index, l ->
            result += Entry(index.toFloat(), l.toFloat())
        }
        return result
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        viewModel.onClickedMarker(marker)
        googleMap?.animateCamera(CameraUpdateFactory.newLatLng(marker.position))
        return true
    }
}
