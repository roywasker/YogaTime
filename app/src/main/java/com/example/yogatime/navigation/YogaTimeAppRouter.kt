package com.example.yogatime.navigation


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen {
    data object SignUpScreen : Screen()
    data object LoginScreen : Screen()
    data object ClientHomeScreen : Screen()
    data object ClientProfileScreen : Screen()
    data object ManagerHomeScreen : Screen()
    data object ManagerProfileScreen : Screen()
    data object GalleryScreen : Screen()
    data object AddNewEventScreen : Screen()
    data object EditUserDataScreen : Screen()
    data object TrainUserDisplayScreen : Screen()

}


object YogaTimeAppRouter {

    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.LoginScreen)

    fun navigateTo(destination : Screen){
        currentScreen.value = destination
    }


}