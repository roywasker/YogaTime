package com.example.yogatime.navigation


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen {
    object SignUpScreen : Screen()
    object LoginScreen : Screen()
    object ClientHomeScreen : Screen()
    object ClientProflieScreen : Screen()
    object HomeScreen2 : Screen()
    object GalleryScreen : Screen()
    object AddNewEventScreen : Screen()

}


object YogaTimeAppRouter {

    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.ClientHomeScreen)

    fun navigateTo(destination : Screen){
        currentScreen.value = destination
    }


}