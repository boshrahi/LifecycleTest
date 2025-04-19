package com.boshra.lifecycletest.usecase.location.domain

import android.location.Location
import com.boshra.lifecycletest.usecase.location.data.LocationRepository
import kotlinx.coroutines.flow.Flow

class GetLocationUpdatesUseCase(
    private val repo: LocationRepository
) {
    operator fun invoke(isFine: Boolean): Flow<Location> =
        repo.getLocationUpdates(isFine)
}