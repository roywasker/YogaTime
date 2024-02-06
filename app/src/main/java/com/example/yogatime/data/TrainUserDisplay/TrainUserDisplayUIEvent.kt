package com.example.yogatime.data.TrainUserDisplay

data class TrainUserDisplayUIEvent (
    var EventName: String = "",
    var EventDate: String = "",
    var EventTime: String = "",
    var NumberOfParticipants: String = "",

    var EventNameError: Boolean = true,
    var EventDateError: Boolean = true,
    var EventTimeError: Boolean = true,
    var NumberOfParticipantsError: Boolean = true,
)