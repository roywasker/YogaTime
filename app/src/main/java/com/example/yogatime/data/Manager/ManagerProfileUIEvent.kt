package com.example.yogatime.data.Manager

sealed class ManagerProfileUIEvent {

    object LogoutButtonClicked : ManagerProfileUIEvent()
    object HomeButtonClicked : ManagerProfileUIEvent()

}