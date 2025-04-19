package com.boshra.lifecycletest

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.lifecycle.*

/**
 * A FrameLayout that:
 *  - owns its own LifecycleRegistry
 *  - contains a final PreviewView
 *  - binds/unbinds a LifecycleCameraController on attach/detach
 *  for Views System
 */
class CameraPreviewWrapper @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle), LifecycleOwner {

    // Our own lifecycle registry
    private val lifecycleRegistry = LifecycleRegistry(this)

    // The CameraController (lifecycle‑aware) that will feed into the PreviewView
    private val cameraController = LifecycleCameraController(context).apply {
        cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    }

    // The actual PreviewView from CameraX
    private val previewView = PreviewView(context).also { pv ->
        pv.layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        // tell the controller where to send the surface
        pv.controller = cameraController
        addView(pv)
    }

    //override fun getLifecycle(): Lifecycle = lifecycleRegistry

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.d(TAG, "onAttachedToWindow ▶ lifecycle ON_CREATE → ON_START")
        // advance our lifecycle so CameraController will start
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START)

        // bind camera to our custom lifecycle
        cameraController.bindToLifecycle(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.d(TAG, "onDetachedFromWindow ▶ unbind → lifecycle ON_STOP → ON_DESTROY")
        // clear use‑cases immediately
        cameraController.unbind()

        // tear down our lifecycle
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP)
        lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }

    companion object {
        private const val TAG = "LifecyclePreviewWrapper"
    }

    override val lifecycle: Lifecycle
        get() = lifecycleRegistry
}
