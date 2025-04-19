// CameraPreviewContent.kt
package com.boshra.lifecycletest.usecase.camera

import androidx.camera.compose.CameraXViewfinder
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceRequest
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun CameraPreviewContent(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current.applicationContext
    val lifecycleOwner = LocalLifecycleOwner.current

    // 1) One Preview use‐case we’ll keep around.
    val previewUseCase = remember { Preview.Builder().build() }

    // 2) State to hold the SurfaceRequest coming from the Preview use‑case.
    var surfaceRequest by remember { mutableStateOf<SurfaceRequest?>(null) }

    // 3) Install a SurfaceProvider so we get a new SurfaceRequest whenever CameraX needs one.
    DisposableEffect(previewUseCase) {
        previewUseCase.setSurfaceProvider { request ->
            surfaceRequest = request
        }
        onDispose { /* nothing to cleanup here */ }
    }

    // 4) Bind to camera when composable enters, unbindAll() when it leaves.
    DisposableEffect(previewUseCase, lifecycleOwner) {
        val providerFuture = ProcessCameraProvider.getInstance(context)
        val provider = providerFuture.get()  // safe on main thread
        provider.bindToLifecycle(
            lifecycleOwner,
            CameraSelector.DEFAULT_BACK_CAMERA,
            previewUseCase
        )
        onDispose {
            provider.unbindAll()  // ⟵ stops the camera when this composable is removed :contentReference[oaicite:0]{index=0}
        }
    }

    // 5) Only show the viewfinder once we’ve got a SurfaceRequest.
    surfaceRequest?.let { req ->
        CameraXViewfinder(
            surfaceRequest = req,
            modifier = modifier
        )
    }
}

