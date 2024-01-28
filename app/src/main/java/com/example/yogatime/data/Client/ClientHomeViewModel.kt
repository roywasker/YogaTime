package com.example.yogatime.data.Client

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AssignmentInd
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Logout
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.yogatime.data.ToolBar
import com.example.yogatime.data.rules.NavigationItem
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ClientHomeViewModel :ViewModel() {

    fun onEvent(event : ClienHomeUIEvent) {
        when (event) {
            is ClienHomeUIEvent.LogoutButtonClicked -> {
                ToolBar.logout()
            }
            is  ClienHomeUIEvent.ProfileButtonClicked ->{
                YogaTimeAppRouter.navigateTo(Screen.ClientProfileScreen)
            }
        }
    }
}