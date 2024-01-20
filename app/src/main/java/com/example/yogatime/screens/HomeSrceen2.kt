package com.example.yogatime.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Surface
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.graphics.Color
import com.example.yogatime.components.HeadingTextComponent


@Composable
fun HomeSrceen2 () {

    Surface(color = Color.White,
    modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
        .padding(18.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            HeadingTextComponent(value = "Home page2")
        }
    }
}