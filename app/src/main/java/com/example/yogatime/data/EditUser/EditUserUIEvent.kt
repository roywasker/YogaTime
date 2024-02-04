package com.example.yogatime.data.EditUser

import com.example.yogatime.data.Client.ClienHomeUIEvent
import com.example.yogatime.data.Client.ClientProfileUIEvent
import com.example.yogatime.data.sighup.SignupUIEvent

sealed class EditUserUIEvent {

    data class fullNameChanged(val fullName:String) : EditUserUIEvent()
    data class emailChanged(val email:String) : EditUserUIEvent()
    data class phoneChanged(val phone:String) : EditUserUIEvent()

    object EditButtonClicked : EditUserUIEvent()

    object LogoutButtonClicked : EditUserUIEvent()
    object ProfileButtonClicked : EditUserUIEvent()
    object HomeButtonClicked : EditUserUIEvent()

}