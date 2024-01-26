package com.example.yogatime.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Surface
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import com.example.yogatime.components.HeadingTextComponent
import com.example.yogatime.components.PickImageFromGallery
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter


@Composable
fun HomeSrceen () {

    Surface(color = Color.White,
    modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(18.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeadingTextComponent(value = "Home page")
            Button(onClick = {
                YogaTimeAppRouter.navigateTo(Screen.AddNewEventScreen)
            }) {
                Text("Go to Add New Event")
            }

            Button(onClick = {
                YogaTimeAppRouter.navigateTo(Screen.GalleryScreen)
            }) {
                Text("Go to Gallery")
            }




        }
    }
}