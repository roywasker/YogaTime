package com.example.yogatime.data.Client

import android.content.ContentValues
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.yogatime.data.ToolBar
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.values
import com.google.firebase.ktx.Firebase
import java.util.Date

class ClientProfileViewModel : ViewModel() {

    var clientRatingUiState = mutableStateOf(ClientProfileUIState())
    private var trainToDeleteReg : RegToTrainState? = null
    val popupMessage = mutableStateOf<String?>(null)


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
            is ClientProfileUIEvent.unRegToTrainButtonClicked ->{
                deleteTrain()
            }
            is ClientProfileUIEvent.trainToDelete ->{
                trainToDeleteReg = event.trainToDelete
            }
        }
    }

    private fun deleteTrain() {
        if (trainToDeleteReg == null) {
            popupMessage.value = "Please select training to cancel registration"
            return
        }
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uid = it.uid
            val database = FirebaseDatabase.getInstance()
            val trainReference =
                trainToDeleteReg?.let { it1 ->
                    database.reference.child("users").child(uid).child("trains").child(
                        it1.trainId)
                }
            trainReference?.setValue(null)
            val databaseRefToUser = trainToDeleteReg?.let { database.reference.child("AddNewEvent").child(it.trainId)
                .child("userRegister").child(uid)}
            databaseRefToUser?.setValue(null)?.addOnSuccessListener {
                popupMessage.value="Train delete successfully"
            }

            updateTrainInDelete()
        }
    }

    private fun updateTrainInDelete() {
        val database = FirebaseDatabase.getInstance()
        val databaseRef = trainToDeleteReg?.let { database.reference.child("AddNewEvent").child(it.trainId)}
        databaseRef?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val newNumOfParticipants = dataSnapshot.child("NumberOfParticipants").value as String
                var newNumOfParticipantsInt = newNumOfParticipants.toInt()
                newNumOfParticipantsInt += 1
                val trainMap = hashMapOf(
                    "NumberOfParticipants" to newNumOfParticipantsInt.toString()
                )
                databaseRef.updateChildren(trainMap as Map<String, Any>).addOnSuccessListener {}
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e(ContentValues.TAG, "Error fetching data: ${error.message}")
            }
        })
    }

    private fun addRating(){
        val user = FirebaseAuth.getInstance().currentUser
        var userEmail = ""
        if (user != null){
            userEmail = user.email.toString()
        }
        val database = Firebase.database
        val reference = database.getReference("rate")
        val id = reference.push().key
        val newReference = id?.let { reference.child(it) }
        val rateMap = hashMapOf(
            "userEmail" to userEmail,
            "rateInString" to clientRatingUiState.value.rateInString,
            "rate" to clientRatingUiState.value.rating,
            "review" to clientRatingUiState.value.review
        )
        newReference?.setValue(rateMap)
        popupMessage.value = "Rating completed successfully"
    }
}