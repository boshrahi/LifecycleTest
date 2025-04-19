package com.boshra.lifecycletest.usecase.location

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Observes lifecycle events and toggles location granularity.
 */
class LocationUpdatesManager(
    lifecycle: Lifecycle,
    private val onGranularityChange: (Boolean) -> Unit
) : LifecycleEventObserver {
    init {
        lifecycle.addObserver(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> {
                Log.d(TAG, "onStateChanged ▶ lifecycle ON_CREATE → ON_START")
                onGranularityChange(true)
            }
            Lifecycle.Event.ON_STOP  -> {
                Log.d(TAG, "onStateChanged ▶ lifecycle ON_CREATE → ON_STOP")
                onGranularityChange(false)
            }
            else -> {}
        }
    }
    companion object {
        private const val TAG = "LocationUpdatesManager"
    }
}