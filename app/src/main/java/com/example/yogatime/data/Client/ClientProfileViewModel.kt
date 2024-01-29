package com.example.yogatime.data.Client

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.yogatime.data.ToolBar
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ClientProfileViewModel : ViewModel() {

    var clientRatingUiState = mutableStateOf(ClientProfileUIState())
    val ratePopupMessage = mutableStateOf<String?>(null)
    fun onEvent(event: ClientProfileUIEvent) {
        when (event) {
            is ClientProfileUIEvent.LogoutButtonClicked -> {
                ToolBar.logout()
            }
            is ClientProfileUIEvent.HomeButtonClicked ->{
                YogaTimeAppRouter.navigateTo(Screen.ClientHomeScreen)
            }
            is ClientProfileUIEvent.addRating ->{
                clientRatingUiState.value = clientRatingUiState.value.copy(
                    rating = event.rating.toString()
                )
            }
            is ClientProfileUIEvent.addReview ->{
                clientRatingUiState.value = clientRatingUiState.value.copy(
                    review = event.review
                )
            }
            is ClientProfileUIEvent.RatingButtonClicked -> {
                when(clientRatingUiState.value.rating){
                    "1" -> {
                        clientRatingUiState.value.rateInString="one"
                    }
                    "2" -> {
                        clientRatingUiState.value.rateInString="two"
                    }
                    "3" -> {
                        clientRatingUiState.value.rateInString="three"
                    }
                    "4" -> {
                        clientRatingUiState.value.rateInString="four"
                    }
                    "5" -> {
                        clientRatingUiState.value.rateInString="five"
                    }
                }
                addRating()
            }
            is ClientProfileUIEvent.EditButtonClicked ->{
                YogaTimeAppRouter.navigateTo(Screen.EditUserDataScreen)
            }
        }
    }

    private fun addRating(){
        val database = Firebase.database
        val reference = database.getReference("rate")
        val id = reference.push().key
        val newReference = id?.let { reference.child(it) }
        val rateMap = hashMapOf(
            "rateInString" to clientRatingUiState.value.rateInString,
            "rate" to clientRatingUiState.value.rating,
            "review" to clientRatingUiState.value.review
        )
        newReference?.setValue(rateMap)
        ratePopupMessage.value = "Rating completed successfully"
    }
}