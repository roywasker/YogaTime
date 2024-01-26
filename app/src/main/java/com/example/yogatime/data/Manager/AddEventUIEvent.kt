package com.example.yogatime.data.Manager


sealed class AddEventUIEvent{
    object LogoutButtonClicked : AddEventUIEvent()
    object ProfileButtonClicked : AddEventUIEvent()
    object HomeButtonClicked : AddEventUIEvent()
}