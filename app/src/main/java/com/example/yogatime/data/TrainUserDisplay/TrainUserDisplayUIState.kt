package com.example.yogatime.data.TrainUserDisplay

import com.example.yogatime.data.AddEvent.AddNewEvent_UIEvent
import com.example.yogatime.data.Manager.TrainUiState

sealed class TrainUserDisplayUIState {
    data class TrainNameChanged(val EventName:String) : TrainUserDisplayUIState()
    data class DateChanged(val EventDate:String) : TrainUserDisplayUIState()
    data class TimeChanged(val EventTime:String) : TrainUserDisplayUIState()

    data class NumberOfParticipantesChanged(val NumberOfParticipants:String) : TrainUserDisplayUIState()

    data class EditButtonClicked(val trainClick : TrainUiState ) : TrainUserDisplayUIState()

    object BackButtonClicked : TrainUserDisplayUIState()

    data class DeleteButtonClicked(val train : TrainUiState ) : TrainUserDisplayUIState()


    object LogoutButtonClicked : TrainUserDisplayUIState()
    object ProfileButtonClicked : TrainUserDisplayUIState()
    object HomeButtonClicked : TrainUserDisplayUIState()
}