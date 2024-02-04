package com.example.yogatime.data.TrainUserDisplay

import androidx.lifecycle.ViewModel
import com.example.yogatime.data.ToolBar
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter

class TrainUserDisplayViewModel : ViewModel() {
    fun onEvent(event : TrainUserDisplayUIState){
        when(event){
            is TrainUserDisplayUIState.LogoutButtonClicked ->{
                ToolBar.logout()
            }
            is TrainUserDisplayUIState.ProfileButtonClicked ->{
                YogaTimeAppRouter.navigateTo(Screen.ManagerProfileScreen)
            }
            is TrainUserDisplayUIState.HomeButtonClicked ->{
                YogaTimeAppRouter.navigateTo(Screen.ManagerHomeScreen)
            }
        }
    }
}