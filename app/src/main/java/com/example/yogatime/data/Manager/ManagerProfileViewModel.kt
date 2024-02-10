package com.example.yogatime.data.Manager

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.yogatime.data.ToolBar
import com.example.yogatime.data.sighup.RegistrationUiState
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ManagerProfileViewModel : ViewModel() {
    var addCoachUiState = mutableStateOf(ManagerProfileUIState())
    var emailPassed = mutableStateOf(false)
    val addCoachPopUp = mutableStateOf<String?>(null)
    fun onEvent(event: ManagerProfileUIEvent) {
        when (event) {
            is ManagerProfileUIEvent.LogoutButtonClicked -> {
                ToolBar.logout()
            }
            is ManagerProfileUIEvent.HomeButtonClicked ->{
                YogaTimeAppRouter.navigateTo(Screen.ManagerHomeScreen)
            }
            is ManagerProfileUIEvent.EmailAdd ->{
                addCoachUiState.value = addCoachUiState.value.copy(
                    emailToCoach = event.emailToCoach
                )
                emailPassed.value = event.emailToCoach.isNotEmpty()
            }
            is ManagerProfileUIEvent.AddCoachButtonClicked ->{
                addCoach()
            }
            is ManagerProfileUIEvent.EditButtonClicked ->{
                YogaTimeAppRouter.navigateTo(Screen.EditUserDataScreen)
            }
        }
    }

    private val TAG = ManagerProfileViewModel::class.simpleName
    private fun addCoach() {
        val database = FirebaseDatabase.getInstance()
        val usersReference = database.reference.child("users")
        val userMap = hashMapOf("isCoach" to true)

        checkIfUserExists(usersReference) { userExist, userNewReference, isCoach ->
            if (userExist) {
                if (isCoach){
                    addCoachPopUp.value = "User is already have coach access"
                }else {
                    userNewReference.updateChildren(userMap as Map<String, Any>)
                        .addOnSuccessListener {
                            addCoachPopUp.value = "User add coach access successfully"
                        }
                }
            } else {
                addCoachPopUp.value = "User not exist"
            }
        }
    }

    private fun checkIfUserExists(
        usersReference: DatabaseReference,
        callback: (Boolean, DatabaseReference, Boolean) -> Unit
    ) {
        var userExist = false
        var userNewReference = usersReference
        var isCoach = false

        usersReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (userSnapshot in dataSnapshot.children) {
                    val user = userSnapshot.getValue(RegistrationUiState::class.java)
                    if (user != null) {
                        if (user.email == addCoachUiState.value.emailToCoach) {
                            userExist = true
                            val userIsCoach = userSnapshot.child("isCoach").value as Boolean
                            if (userIsCoach) {
                                isCoach = true
                            } else {
                                userNewReference = userSnapshot.key?.let { usersReference.child(it) }!!
                            }
                        }
                    }
                }
                callback.invoke(userExist, userNewReference,isCoach)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(TAG, "failed add coach access")
                callback.invoke(false, usersReference,false)
            }
        })
    }

}