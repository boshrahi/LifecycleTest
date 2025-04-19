package com.boshra.lifecycletest.usecase.location

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boshra.lifecycletest.usecase.location.domain.GetLocationUpdatesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationViewModel(
    private val getLocationUpdates: GetLocationUpdatesUseCase
) : ViewModel() {

    private val fineGrainedFlow = MutableStateFlow(false)
    private val _locationState = MutableStateFlow<Location?>(null)
    val locationState: StateFlow<Location?> = _locationState

    init {
        viewModelScope.launch {
            fineGrainedFlow
                .flatMapLatest { isFine -> getLocationUpdates(isFine) }
                .collect { loc ->
                    Log.d("LocationViewModel latitude: ", loc.latitude.toString())
                    Log.d("LocationViewModel provider: ", loc.provider.toString())
                    _locationState.update { loc }
                }
        }
    }

    /** Called by [LocationUpdatesManager] */
    fun setFineGrained(enabled: Boolean) {
        fineGrainedFlow.update { enabled }
    }
}
