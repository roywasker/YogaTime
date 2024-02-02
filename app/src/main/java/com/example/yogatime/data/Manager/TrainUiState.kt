package com.example.yogatime.data.Manager

data class TrainUiState(
    var TrainId : String = "",
    var UserEmail: String = "",
    var EventName: String = "",
    var EventDate: String = "",
    var EventTime: String = "",
    var NumberOfParticipants: String = "",
    var userList: List<userRegState>? = null
)