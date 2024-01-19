package com.example.yogatime.navigation


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen {
    object SignUpScreen : Screen()
    object LoginScreen : Screen()
}


object YogaTimeAppRouter {

    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.LoginScreen)

    fun navigateTo(destination : Screen){
        currentScreen.value = destination
    }


}