package com.example.yogatime.data.Manager


sealed class ManagerProfileUIEvent {

    object LogoutButtonClicked : ManagerProfileUIEvent()
    object HomeButtonClicked : ManagerProfileUIEvent()
    object EditButtonClicked : ManagerProfileUIEvent()
    data class EmailAdd(val emailToCoach:String) : ManagerProfileUIEvent()

    object AddCoachButtonClicked : ManagerProfileUIEvent()

}