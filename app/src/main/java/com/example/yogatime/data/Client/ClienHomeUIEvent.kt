package com.example.yogatime.data.Client


sealed class ClienHomeUIEvent {
    object LogoutButtonClicked : ClienHomeUIEvent()
    object ProfileButtonClicked : ClienHomeUIEvent()
}