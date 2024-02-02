package com.example.yogatime.data.TrainUserDisplay

import com.example.yogatime.data.AddEvent.AddNewEvent_UIEvent

sealed class TrainUserDisplayUIState {
    object LogoutButtonClicked : TrainUserDisplayUIState()
    object ProfileButtonClicked : TrainUserDisplayUIState()
    object HomeButtonClicked : TrainUserDisplayUIState()
}