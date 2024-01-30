package com.example.yogatime.data.AddEvent

import java.io.InputStream

data class AddNewEventState(
    var EventName: String = "",
    var EventDate: String = "",
    var EventTime: String = "",
    var NumberOfParticipants: String = "",

    var EventNameError: Boolean = false,
    var EventDateError: Boolean = false,
    var EventTimeError: Boolean = false,
    var NumberOfParticipantsError: Boolean = false
)