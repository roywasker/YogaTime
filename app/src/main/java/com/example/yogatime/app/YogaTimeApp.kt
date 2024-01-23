package com.example.yogatime.app

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter
import com.example.yogatime.screens.HomeSrceen
import com.example.yogatime.screens.HomeSrceen2
import com.example.yogatime.screens.LoginScreen
import com.example.yogatime.screens.SighUpScreen

@Composable
fun YogaTimeApp(){
    Surface(
        modifier = Modifier.fillMaxSize() ,
        color = Color.White
    ) {
        Crossfade(targetState = YogaTimeAppRouter.currentScreen, label = "") { currentState ->
            when(currentState.value){
                is Screen.SignUpScreen ->{
                    SighUpScreen()
                }
                is Screen.LoginScreen ->{
                    LoginScreen()
                }
                is Screen.HomeScreen ->{
                    HomeSrceen()
                }
                is Screen.HomeScreen2 ->{
                    HomeSrceen2()
                }
            }
        }
    }
}