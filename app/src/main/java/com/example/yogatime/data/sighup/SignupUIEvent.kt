package com.example.yogatime.data.sighup

sealed class SignupUIEvent {

    data class fullNameChanged(val fullName:String) : SignupUIEvent()
    data class emailChanged(val email:String) : SignupUIEvent()
    data class phoneChanged(val phone:String) : SignupUIEvent()
    data class passwordChanged(val password:String) : SignupUIEvent()

    data class dateOfBirthChanged(val dateOfBirth:String) : SignupUIEvent()

    object RegisterButtonClicked : SignupUIEvent()

}