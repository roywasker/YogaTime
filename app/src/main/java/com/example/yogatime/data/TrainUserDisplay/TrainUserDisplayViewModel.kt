package com.example.yogatime.data.TrainUserDisplay

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.yogatime.data.Manager.TrainUiState
import com.example.yogatime.data.ToolBar
import com.example.yogatime.data.rules.rules_new_event
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter
import com.google.firebase.database.FirebaseDatabase

class TrainUserDisplayViewModel : ViewModel() {
    var editTrainDataUiState = mutableStateOf(TrainUserDisplayUIEvent())

    var allValidationsPassed = mutableStateOf(true)
    private val TAG = TrainUserDisplayViewModel::class.simpleName

    fun onEvent(event : TrainUserDisplayUIState){
        when(event){
            is TrainUserDisplayUIState.TrainNameChanged ->{
                editTrainDataUiState.value = editTrainDataUiState.value.copy(
                    EventName = event.EventName
                )
                val EventNameResult = rules_new_event.validatorEventName(
                    EventName = event.EventName
                )
                editTrainDataUiState.value = editTrainDataUiState.value.copy(
                    EventNameError = EventNameResult.status
                )
                allValidationsPassed.value = EventNameResult.status
            }
            is TrainUserDisplayUIState.DateChanged ->{
                editTrainDataUiState.value = editTrainDataUiState.value.copy(
                    EventDate = event.EventDate
                )
                val EventDateResult = rules_new_event.validatorEventDate(
                    EventDate = event.EventDate
                )
                editTrainDataUiState.value = editTrainDataUiState.value.copy(
                    EventDateError = EventDateResult.status
                )
                allValidationsPassed.value = EventDateResult.status
            }
            is TrainUserDisplayUIState.TimeChanged ->{
                editTrainDataUiState.value = editTrainDataUiState.value.copy(
                    EventTime = event.EventTime
                )
                val EventTimeResult = rules_new_event.validatorEventTime(
                    EventTime = event.EventTime
                )
                editTrainDataUiState.value = editTrainDataUiState.value.copy(
                    EventTimeError = EventTimeResult.status
                )
                allValidationsPassed.value = EventTimeResult.status
            }
            is TrainUserDisplayUIState.NumberOfParticipantesChanged ->{
                editTrainDataUiState.value = editTrainDataUiState.value.copy(
                    NumberOfParticipants = event.NumberOfParticipants
                )
                val NumberOfParticipantsResult = rules_new_event.validatorNumberOfParticipants(
                    NumberOfParticipants = event.NumberOfParticipants
                )
                editTrainDataUiState.value = editTrainDataUiState.value.copy(
                    NumberOfParticipantsError = NumberOfParticipantsResult.status
                )
                allValidationsPassed.value = NumberOfParticipantsResult.status
            }
            is TrainUserDisplayUIState.EditButtonClicked ->{

                editTrain(event.trainClick)
            }
            is TrainUserDisplayUIState.BackButtonClicked ->{
                YogaTimeAppRouter.navigateTo(Screen.ManagerHomeScreen)
            }
            is TrainUserDisplayUIState.LogoutButtonClicked ->{
                ToolBar.logout()
            }
            is TrainUserDisplayUIState.ProfileButtonClicked ->{
                YogaTimeAppRouter.navigateTo(Screen.ManagerProfileScreen)
            }
            is TrainUserDisplayUIState.HomeButtonClicked ->{
                YogaTimeAppRouter.navigateTo(Screen.ManagerHomeScreen)
            }
            is TrainUserDisplayUIState.DeleteButtonClicked ->{
                deleteTrain(event.train)
            }
        }
    }

    private fun editTrain(trainClick: TrainUiState) {
        var EventName = trainClick.EventName
        var EventDate = trainClick.EventDate
        var EventTime = trainClick.EventTime
        var NumberOfParticipants = trainClick.NumberOfParticipants
        if (editTrainDataUiState.value.EventName.isNotEmpty()) {
            EventName = editTrainDataUiState.value.EventName
        }
        if (editTrainDataUiState.value.EventDate.isNotEmpty()) {
            EventDate = editTrainDataUiState.value.EventDate
        }
        if (editTrainDataUiState.value.EventTime.isNotEmpty()) {
            EventTime = editTrainDataUiState.value.EventTime
        }
        if (editTrainDataUiState.value.NumberOfParticipants.isNotEmpty()) {
            NumberOfParticipants = editTrainDataUiState.value.NumberOfParticipants
        }

        if (EventName != null && EventDate != null && EventTime != null && NumberOfParticipants != null) {
            Log.d(TAG, "Updating train testtt: $EventName")
            updateTrainInFirebase(
                EventName = EventName,
                EventDate = EventDate,
                EventTime = EventTime,
                NumberOfParticipants = NumberOfParticipants, curTrain = trainClick
            )
        } else {
            Log.d(TAG, "Error updating train: ")
        }
    }

    private fun updateTrainInFirebase(EventName: String, EventDate: String, EventTime: String, NumberOfParticipants: String, curTrain: TrainUiState) {

        val database = FirebaseDatabase.getInstance()
        val databaseRef = database.reference.child("AddNewEvent")
        val currentTrainRef = databaseRef.child(curTrain.TrainId)
        // Create a map to update all fields at once
        val trainUpdateMap = hashMapOf<String, Any>(
            "EventName" to EventName,
            "EventDate" to EventDate,
            "EventTime" to EventTime,
            "NumberOfParticipants" to NumberOfParticipants
        )
        // Update the child with new data
        currentTrainRef.updateChildren(trainUpdateMap as Map<String, Any>).addOnSuccessListener {
            Log.d(TAG, "Train updated successfully: $EventName")
            Log.d(TAG, "Train Id: ${curTrain.TrainId}")

            YogaTimeAppRouter.navigateTo(Screen.ManagerHomeScreen)
        }.addOnFailureListener {
            Log.d(TAG, "Error updating train: ", it)
        }


    }

    private fun deleteTrain(train: TrainUiState) {
        val database = FirebaseDatabase.getInstance()
        val databaseRef = database.reference.child("AddNewEvent")
        val currentTrainRef = databaseRef.child(train.TrainId)
        currentTrainRef.removeValue().addOnSuccessListener {
            Log.d(TAG, "Train deleted successfully: ${train.EventName}")
            YogaTimeAppRouter.navigateTo(Screen.ManagerHomeScreen)
        }.addOnFailureListener {
            Log.d(TAG, "Error deleting train: ", it)
        }
    }


}



