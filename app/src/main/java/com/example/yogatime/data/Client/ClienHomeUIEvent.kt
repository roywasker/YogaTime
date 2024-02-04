package com.example.yogatime.data.Client


sealed class ClienHomeUIEvent {
    object LogoutButtonClicked : ClienHomeUIEvent()
    object ProfileButtonClicked : ClienHomeUIEvent()
    data class trainId(val trainId: RegToTrainState): ClienHomeUIEvent()

    object regToTrainButtonClicked : ClienHomeUIEvent()

}