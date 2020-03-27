package com.kiyosuke.corona_grapher.ui.map

import androidx.lifecycle.*
import com.google.android.gms.maps.model.Marker
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kiyosuke.corona_grapher.data.repository.CoronavirusRepository
import com.kiyosuke.corona_grapher.model.LoadState
import com.kiyosuke.corona_grapher.model.Location
import com.kiyosuke.corona_grapher.model.LocationId
import com.kiyosuke.corona_grapher.util.livedata.Event
import kotlinx.coroutines.launch
import timber.log.Timber

class MapViewModel(
    private val repo: CoronavirusRepository
) : ViewModel() {

    val locations = repo.locations().asLiveData(viewModelScope.coroutineContext)

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    private val _sheetInfo = MutableLiveData<Location>()
    val sheetInfo: LiveData<Location> get() = _sheetInfo

    private val _bottomSheetState = MutableLiveData<Event<Int>>()
    val bottomSheetState: LiveData<Event<Int>> get() = _bottomSheetState

    private val _locationDetail = MutableLiveData<LoadState<Location.Detail>>()
    val locationDetail: LiveData<LoadState<Location.Detail>> get() = _locationDetail

    fun refresh() {
        _bottomSheetState.value = Event(BottomSheetBehavior.STATE_HIDDEN)
        viewModelScope.launch {
            try {
                repo.refreshLocations()
            } catch (e: Exception) {
                // TODO: エラーメッセージを生成
                Timber.e(e)
            }
        }
    }

    fun onClickedMarker(marker: Marker) {
        val location = marker.tag as? Location ?: return
        _sheetInfo.value = location
        _bottomSheetState.value = Event(BottomSheetBehavior.STATE_COLLAPSED)
        requestLocationDetail(location.id)
    }

    private fun requestLocationDetail(locationId: LocationId) {
        viewModelScope.launch {
            _locationDetail.value = LoadState.Loading
            try {
                _locationDetail.value = LoadState.Loaded(repo.location(locationId))
            } catch (e: Exception) {
                _locationDetail.value = LoadState.Error(e)
            }
        }
    }
}
