package com.example.yogatime.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Surface
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.graphics.Color
import com.example.yogatime.components.HeadingTextComponent
import com.example.yogatime.components.PickImageFromGallery
import com.example.yogatime.data.gallery.GalleryScreenViewModel

import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.yogatime.components.SinglePhotoPicker


@Composable
fun GalleryScreen(viewModel: GalleryScreenViewModel = viewModel()) {
    Surface(
        color = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(18.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeadingTextComponent(value = "Upload Image to Gallery")
            SinglePhotoPicker()


//            // Display images
//            LazyColumn {
//                items(imageUrls) { imageUrl ->
//                    Image(
//                        painter = rememberImagePainter(imageUrl),
//                        contentDescription = null,
//                        modifier = Modifier.size(100.dp).padding(4.dp)
//                    )
//                }
//            }
        }
    }
}
