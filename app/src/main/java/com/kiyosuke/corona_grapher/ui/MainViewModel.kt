package com.kiyosuke.corona_grapher.ui

import androidx.lifecycle.*
import com.kiyosuke.corona_grapher.data.repository.CoronavirusRepository
import com.kiyosuke.corona_grapher.model.LoadState
import com.kiyosuke.corona_grapher.model.Location
import com.kiyosuke.corona_grapher.model.LocationId
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(
    private val repo: CoronavirusRepository
) : ViewModel() {

    val locations = repo.locations().asLiveData(viewModelScope.coroutineContext)

    private val _locationLoadState = MutableLiveData<LoadState<Location.Detail>>()
    val locationLoadState: LiveData<LoadState<Location.Detail>> get() = _locationLoadState

    fun refresh() {
        Timber.d("refresh()")
        viewModelScope.launch {
            try {
                repo.refreshLocations()
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    fun requestDetail(locationId: LocationId) {
        viewModelScope.launch {
            _locationLoadState.value = LoadState.Loading
            try {
                val location = repo.location(locationId)
                _locationLoadState.value = LoadState.Loaded(location)
            } catch (e: Exception) {
                _locationLoadState.value = LoadState.Error(e)
            }
        }
    }
}