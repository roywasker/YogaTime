package com.example.yogatime.data.AddEvent


data class AddNewEventState(
    var UserEmail : String = "",
    var EventName: String = "",
    var EventDate: String = "",
    var EventTime: String = "",
    var NumberOfParticipants: String = "",

    var EventNameError: Boolean = false,
    var EventDateError: Boolean = false,
    var EventTimeError: Boolean = false,
    var NumberOfParticipantsError: Boolean = false
)