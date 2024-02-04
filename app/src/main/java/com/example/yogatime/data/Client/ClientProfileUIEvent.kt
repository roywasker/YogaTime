package com.example.yogatime.data.Client

sealed class ClientProfileUIEvent {

    data class addRating(val rating : Int):ClientProfileUIEvent()
    data class addReview(val review : String):ClientProfileUIEvent()
    data class trainToDelete(val trainToDelete: RegToTrainState): ClientProfileUIEvent()

    object LogoutButtonClicked : ClientProfileUIEvent()
    object HomeButtonClicked : ClientProfileUIEvent()

    object RatingButtonClicked : ClientProfileUIEvent()

    object EditButtonClicked : ClientProfileUIEvent()
    object unRegToTrainButtonClicked : ClientProfileUIEvent()
}