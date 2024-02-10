package com.example.yogatime.data.AddEvent

sealed class AddNewEvent_UIEvent {

    data class eventNameChanged(val EventName:String) : AddNewEvent_UIEvent()
    data class dateChanged(val EventDate:String) : AddNewEvent_UIEvent()
    data class timeChanged(val EventTime:String) : AddNewEvent_UIEvent()
    data class NumberOfParticipantChanged(val NumberOfParticipants:String) : AddNewEvent_UIEvent()

    object AddNewEventButtonClicked : AddNewEvent_UIEvent()
    object LogoutButtonClicked : AddNewEvent_UIEvent()
    object ProfileButtonClicked : AddNewEvent_UIEvent()
    object HomeButtonClicked : AddNewEvent_UIEvent()
    object BackButtonClicked : AddNewEvent_UIEvent()
}