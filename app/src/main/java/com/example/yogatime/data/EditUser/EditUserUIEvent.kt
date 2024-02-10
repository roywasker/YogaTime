package com.example.yogatime.data.EditUser


sealed class EditUserUIEvent {

    data class fullNameChanged(val fullName:String) : EditUserUIEvent()
    data class emailChanged(val email:String) : EditUserUIEvent()
    data class phoneChanged(val phone:String) : EditUserUIEvent()

    object EditButtonClicked : EditUserUIEvent()

    object LogoutButtonClicked : EditUserUIEvent()
    object ProfileButtonClicked : EditUserUIEvent()
    object HomeButtonClicked : EditUserUIEvent()

    object BackButtonClicked : EditUserUIEvent()

}