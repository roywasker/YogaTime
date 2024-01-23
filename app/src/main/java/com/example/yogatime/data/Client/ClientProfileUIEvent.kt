package com.example.yogatime.data.Client

sealed class ClientProfileUIEvent {

    object LogoutButtonClicked : ClientProfileUIEvent()
    object HomeButtonClicked : ClientProfileUIEvent()

}