package com.example.yogatime.data.AddEvent

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.yogatime.data.ToolBar
import com.example.yogatime.data.rules.Validator
import com.example.yogatime.data.rules.rules_new_event
import com.example.yogatime.data.sighup.RegistrationUiState
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddNewEventScreenViewModel : ViewModel(){
    var AddNewEventState = mutableStateOf(AddNewEventState())
    var allValidationsPassed = mutableStateOf(false)
    val popupMessage = mutableStateOf<String?>(null)

    fun onEvent(event : AddNewEvent_UIEvent){
        when(event){
     is AddNewEvent_UIEvent.eventNameChanged ->{
                AddNewEventState.value = AddNewEventState.value.copy(
                    EventName = event.EventName
                )
                validateDataWithRules()
            }
            is AddNewEvent_UIEvent.dateChanged ->{
                AddNewEventState.value = AddNewEventState.value.copy(
                    EventDate = event.EventDate
                )
                validateDataWithRules()
            }
            is AddNewEvent_UIEvent.timeChanged ->{
                AddNewEventState.value = AddNewEventState.value.copy(
                    EventTime = event.EventTime
                )
                validateDataWithRules()
            }
            is AddNewEvent_UIEvent.NumberOfParticipantChanged ->{
                AddNewEventState.value = AddNewEventState.value.copy(
                    NumberOfParticipants = event.NumberOfParticipants
                )
                validateDataWithRules()
            }
            is AddNewEvent_UIEvent.AddNewEventButtonClicked ->{
                AddNewEvent()
            }
            is AddNewEvent_UIEvent.LogoutButtonClicked ->{
                ToolBar.logout()
            }
            is AddNewEvent_UIEvent.ProfileButtonClicked ->{
                YogaTimeAppRouter.navigateTo(Screen.ManagerProfileScreen)
            }
            is AddNewEvent_UIEvent.HomeButtonClicked ->{
                YogaTimeAppRouter.navigateTo(Screen.ManagerHomeScreen)
            }

        }
    }

    private fun AddNewEvent() {
        createEventInFirebase(
            EventName = AddNewEventState.value.EventName,
            EventDate = AddNewEventState.value.EventDate,
            EventTime = AddNewEventState.value.EventTime,
            NumberOfParticipants = AddNewEventState.value.NumberOfParticipants
        )

    }

    private fun createEventInFirebase(EventName: String, EventDate: String, EventTime: String, NumberOfParticipants: String) {
        val user = FirebaseAuth.getInstance().currentUser
        var userEmail = ""
        if (user != null){
            userEmail = user.email.toString()
        }
        val databaseRef = FirebaseDatabase.getInstance().getReference("AddNewEvent")
        val EventId = databaseRef.push().key ?: return

        val EventInfo = hashMapOf(
            "TrainId" to EventId,
            "UserEmail" to userEmail,
            "EventName" to EventName,
            "EventDate" to EventDate,
            "EventTime" to EventTime,
            "NumberOfParticipants" to NumberOfParticipants
        )

        databaseRef.child(EventId).setValue(EventInfo).addOnSuccessListener {
            YogaTimeAppRouter.navigateTo(Screen.ManagerHomeScreen)

        }.addOnFailureListener {
            popupMessage.value = "Failure to add new train"
        }
    }


    private fun validateDataWithRules() {
        val EventNameResult = rules_new_event.validatorEventName(
            EventName = AddNewEventState.value.EventName
        )
        val EventDateResult = rules_new_event.validatorEventDate(
            EventDate = AddNewEventState.value.EventDate
        )
        val EventTimeResult = rules_new_event.validatorEventTime(
            EventTime = AddNewEventState.value.EventTime
        )
        val NumberOfParticipantsResult = rules_new_event.validatorNumberOfParticipants(
            NumberOfParticipants = AddNewEventState.value.NumberOfParticipants
        )

        AddNewEventState.value = AddNewEventState.value.copy(
            EventNameError = EventNameResult.status,
            EventDateError = EventDateResult.status,
            EventTimeError = EventTimeResult.status,
            NumberOfParticipantsError = NumberOfParticipantsResult.status
        )


       allValidationsPassed.value = EventNameResult.status &&EventDateResult.status && EventTimeResult.status && NumberOfParticipantsResult.status

}

    }