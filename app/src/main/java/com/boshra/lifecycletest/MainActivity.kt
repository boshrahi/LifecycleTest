package com.boshra.lifecycletest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.boshra.lifecycletest.usecase.location.LocationScreen
import com.boshra.lifecycletest.usecase.location.LocationViewModel
import com.boshra.lifecycletest.usecase.location.data.LocationRepositoryImpl
import com.boshra.lifecycletest.usecase.location.domain.GetLocationUpdatesUseCase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Simple manual DI:
            val repo = LocationRepositoryImpl(this)
            val useCase = GetLocationUpdatesUseCase(repo)
            val vmFactory = object : ViewModelProvider.Factory {
                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return LocationViewModel(useCase) as T
                }
            }

            setContent {
                val viewModel = ViewModelProvider(
                    this,
                    vmFactory
                )[LocationViewModel::class.java]
                LocationScreen(viewModel)
            }
            //----------------------- Camera UseCase ----------------------
            /*val context = LocalContext.current
            var hasPermission by remember {
                mutableStateOf(
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                            == PackageManager.PERMISSION_GRANTED
                )
            }
            val permLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { granted -> hasPermission = granted }

            // Request if needed:
            LaunchedEffect(Unit) {
                if (!hasPermission) permLauncher.launch(Manifest.permission.CAMERA)
            }

            if (hasPermission) {
                CameraListScreen()
            } else {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Camera permission required")
                }
            }*/
        }
    }
}
