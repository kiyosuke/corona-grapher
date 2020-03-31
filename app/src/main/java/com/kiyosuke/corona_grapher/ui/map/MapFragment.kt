package com.kiyosuke.corona_grapher.ui.map

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.view.drawToBitmap
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
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
import com.kiyosuke.corona_grapher.common.atSystemDefault
import com.kiyosuke.corona_grapher.common.atUTC
import com.kiyosuke.corona_grapher.databinding.CircleMarkerLayoutBinding
import com.kiyosuke.corona_grapher.databinding.MapFragmentBinding
import com.kiyosuke.corona_grapher.model.*
import com.kiyosuke.corona_grapher.util.color.Color
import com.kiyosuke.corona_grapher.util.ext.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.threeten.bp.format.DateTimeFormatter
import kotlin.math.abs
import kotlin.math.max

class MapFragment : Fragment(R.layout.map_fragment),
    OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener,
    GoogleMap.OnMapClickListener {

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

        setupLineChart()
        setupBarChart()

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
                val rotation = when (state) {
                    BottomSheetBehavior.STATE_EXPANDED -> 0f
                    BottomSheetBehavior.STATE_COLLAPSED -> 180f
                    BottomSheetBehavior.STATE_HIDDEN -> 180f
                    else -> return
                }
                binding.expandIcon.animate().rotationX(rotation).start()
            }
        }

        bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback)

        binding.markerTypeCardView.setOnClickListener {
            MarkerTypeDialogFragment().show(childFragmentManager, "MARKER_TYPE")
        }

        viewModel.locations.observeNonNull(viewLifecycleOwner) { locations ->
            updateLocations(viewModel.markerType.requireValue(), locations)
        }

        viewModel.markerType.observeNonNull(viewLifecycleOwner) { markerType ->
            val locations = viewModel.locations.value ?: return@observeNonNull
            updateLocations(markerType, locations)
        }

        viewModel.bottomSheetState.observeNonNull(viewLifecycleOwner) { event ->
            val state = event.getContentIfNotHandled() ?: return@observeNonNull
            bottomSheetBehavior.state = state
        }

        viewModel.sheetInfo.observeNonNull(viewLifecycleOwner) { info ->
            updateSheetInfo(info)
        }

        viewModel.locationDetail.observeNonNull(viewLifecycleOwner) { state ->
            updateSheetCharts(state)
        }

        viewModel.message.observeNonNull(viewLifecycleOwner) { message ->

        }

        viewModel.refresh()
    }

    private fun setupLineChart() {
        binding.timelineChart.description.isEnabled = false
        binding.timelineChart.setTouchEnabled(false)
    }

    private fun setupBarChart() {
        binding.timelineBarChart.description.isEnabled = false
        binding.timelineBarChart.setTouchEnabled(false)
        binding.timelineBarChart.axisLeft.mAxisMinimum = 0f
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        this.googleMap?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(40.0, 140.0)))

        this.googleMap?.setOnMarkerClickListener(this)
        this.googleMap?.setOnMapClickListener(this)
    }

    private fun requireMapFragment(): SupportMapFragment {
        return childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
    }

    private fun findGoogleLogo(): ImageView? {
        return requireMapFragment().view?.findViewWithTag("GoogleWatermark")
    }

    private fun updateLocations(markerType: MarkerType, locations: List<Location>) {
        this.googleMap?.clear()
        locations.forEach { location ->
            val options = createMarkerOptions(markerType, location)
            val marker = this.googleMap?.addMarker(options)
            marker?.tag = location
        }
    }

    private fun createMarkerOptions(markerType: MarkerType, location: Location): MarkerOptions {
        val data = when (markerType) {
            MarkerType.CONFIRMED -> Color(getColor(R.color.danger)) to location.latest.confirmed
            MarkerType.DEATHS -> Color(getColor(R.color.danger)) to location.latest.deaths
            MarkerType.RECOVERED -> Color(getColor(R.color.safety)) to location.latest.recovered
        }
        val (baseColor, count) = data
        val icon = createMarkerIcon(baseColor, count)
        return MarkerOptions().apply {
            position(location.coordinates.toLatLng())
            title(location.country)
            snippet(location.countryCode)
            icon(BitmapDescriptorFactory.fromBitmap(icon))
        }
    }

    private fun createMarkerIcon(baseColor: Color, count: Long): Bitmap {
        circleView.textCount.text = count.toString()
        val color = when {
            count >= 10000 -> baseColor.darker
            count >= 1000 -> baseColor
            count >= 100 -> baseColor.brighter
            else -> baseColor.brighter.brighter
        }
        circleView.circleFrame.circleColor = color.color

        // Bitmapに変換するために描画する必要あり
        circleView.circleFrame.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        circleView.circleFrame.layout(
            0,
            0,
            circleView.circleFrame.measuredWidth,
            circleView.circleFrame.measuredHeight
        )

        return circleView.circleFrame.drawToBitmap()
    }

    private fun updateSheetInfo(markerInfo: MarkerInfo) {
        binding.textCountry.text = markerInfo.locationName
        binding.textConfirmedCount.text = markerInfo.confirmed.toString()
        binding.textDeathsCount.text = markerInfo.deaths.toString()
        binding.textRecoveredCount.text = markerInfo.recovered.toString()
        binding.updatedDateGroup.isVisible = markerInfo.lastUpdated != null
        markerInfo.lastUpdated?.let { lastUpdated ->
            binding.textUpdatedDate.text = lastUpdated
                .atSystemDefault()
                .format(UPDATED_DATE_FORMATTER)
        }

        binding.nestedScrollView.isVisible = markerInfo.hasDescription
        binding.expandIcon.isVisible = markerInfo.hasDescription
    }

    private fun updateSheetCharts(state: LoadState<Location.Detail>) {
        if (state.isLoading) {
            binding.timelineChart.clear()
            binding.timelineBarChart.clear()
        }
        binding.detailProgressbar.isVisible = state.isLoading
        binding.nestedScrollView.isVisible = state.isLoading.not()

        val detail = state.getValueOrNull() ?: return
        updateTimelineChart(detail.timelines)
        updateTimelineBarChart(detail.timelines)
    }

    private fun updateTimelineChart(timelines: Timelines) {
        val confirmedSet = createLineSet(
            createTimelineEntries(timelines.confirmed),
            getString(R.string.confirmed_count),
            getColor(R.color.confirmed)
        )
        val deathsSet = createLineSet(
            createTimelineEntries(timelines.deaths),
            getString(R.string.deaths_count),
            getColor(R.color.deaths)
        )
        val recoveredSet = createLineSet(
            createTimelineEntries(timelines.recovered),
            getString(R.string.recovered_count),
            getColor(R.color.recovered)
        )
        val lineData = LineData(confirmedSet, deathsSet, recoveredSet)

        // X軸の日付生成
        val dates = timelines.confirmed.timeline.keys.map { instant ->
            instant.atUTC().format(DATE_FORMATTER)
        }
        binding.timelineChart.xAxis.valueFormatter = IndexAxisValueFormatter(dates)

        binding.timelineChart.data = lineData
        binding.timelineChart.invalidate()
    }

    private fun createTimelineEntries(timeline: Timeline): List<Entry> {
        val result = mutableListOf<Entry>()
        timeline.timeline.values.forEachIndexed { index, l ->
            result += Entry(index.toFloat(), l.toFloat())
        }
        return result
    }

    private fun createLineSet(entries: List<Entry>, label: String, color: Int): LineDataSet {
        return LineDataSet(entries, label).apply {
            this.color = color
            setDrawCircles(false)
        }
    }

    private fun updateTimelineBarChart(timelines: Timelines) {
        binding.timelineBarChart.apply {
            xAxis.apply {
                val dates = timelines.confirmed.timeline.keys.map {
                    it.atUTC().format(DATE_FORMATTER)
                }.dropLast(1)
                this.valueFormatter = IndexAxisValueFormatter(dates)
                this.position = XAxis.XAxisPosition.BOTTOM
                this.setDrawGridLines(true)
            }
        }
        val confirmedSet = createBarDataSet(
            createBarEntries(timelines.confirmed),
            getString(R.string.confirmed_count),
            getColor(R.color.confirmed)
        )

        binding.timelineBarChart.data = BarData(confirmedSet)
        binding.timelineBarChart.invalidate()
    }

    private fun createBarEntries(timeline: Timeline): List<BarEntry> {
        val diffs = timeline.timeline.values.zipWithNext { first, second -> second - first }
        return diffs.mapIndexed { index, count ->
            BarEntry(index.toFloat(), count.toFloat())
        }
    }

    private fun createBarDataSet(entries: List<BarEntry>, label: String, color: Int): BarDataSet {
        return BarDataSet(entries, label).apply {
            this.color = color
            setDrawValues(false)
        }
    }

    private fun getColor(@ColorRes resId: Int) = requireContext().getColorCompat(resId)

    override fun onMarkerClick(marker: Marker): Boolean {
        viewModel.onMarkerClicked(marker)
        googleMap?.animateCamera(CameraUpdateFactory.newLatLng(marker.position))
        return true
    }

    override fun onMapClick(latlng: LatLng) {
        viewModel.onMapClicked()
    }

    companion object {
        private val UPDATED_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
        private val DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd")
    }
}
