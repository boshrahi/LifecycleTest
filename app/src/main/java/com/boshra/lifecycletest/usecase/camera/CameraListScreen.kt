package com.boshra.lifecycletest.usecase.camera

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CameraListScreen(
    //viewModel: CameraPreviewViewModel = remember { CameraPreviewViewModel() }
) {
    val items = List(40) { "Item #$it" }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        itemsIndexed(items) { index, item ->
            if (index == 0) {
                Card(
                    elevation = CardDefaults.cardElevation(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(8.dp)
                ) {
                    // **PURE COMPOSE** camera preview
                    CameraPreviewContent(
                        //viewModel = viewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                    /*AndroidView(
                        factory = { ctx ->
                            CameraPreviewWrapper(ctx)
                        },
                        modifier = Modifier.fillMaxSize()
                    )*/
                }
            } else {
                Card(
                    elevation = CardDefaults.cardElevation(2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = item,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}
