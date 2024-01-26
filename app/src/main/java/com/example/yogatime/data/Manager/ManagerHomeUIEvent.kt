package com.example.yogatime.data.Manager


sealed class ManagerHomeUIEvent {
    object LogoutButtonClicked : ManagerHomeUIEvent()
    object ProfileButtonClicked : ManagerHomeUIEvent()
}