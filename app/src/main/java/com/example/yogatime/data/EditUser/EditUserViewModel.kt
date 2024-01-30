package com.example.yogatime.data.EditUser

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.yogatime.data.Client.ClientProfileUIState
import com.example.yogatime.data.ToolBar
import com.example.yogatime.data.rules.Validator
import com.example.yogatime.data.sighup.RegistrationUiState
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EditUserViewModel: ViewModel() {

    var editUserDataUiState = mutableStateOf(EditUserUIState())
    var allValidationsPassed = mutableStateOf(true)

    fun onEvent(event : EditUserUIEvent){
        when(event){
            is EditUserUIEvent.fullNameChanged ->{
                editUserDataUiState.value = editUserDataUiState.value.copy(
                    fullName = event.fullName
                )
                val fullNameResult = Validator.validatorFullName(
                    fullName = event.fullName
                )
                editUserDataUiState.value = editUserDataUiState.value.copy(
                    fullNameError = fullNameResult.status
                )
                allValidationsPassed.value = fullNameResult.status
            }
            is EditUserUIEvent.emailChanged ->{
                editUserDataUiState.value = editUserDataUiState.value.copy(
                    email = event.email
                )
                val emailResult = Validator.validatorEmail(
                    email = editUserDataUiState.value.email
                )
                editUserDataUiState.value = editUserDataUiState.value.copy(
                    emailError = emailResult.status
                )
                allValidationsPassed.value = emailResult.status
            }
            is EditUserUIEvent.phoneChanged ->{
                editUserDataUiState.value = editUserDataUiState.value.copy(
                    phone = event.phone
                )
                val phoneResult = Validator.validatorPhone(
                    phone = editUserDataUiState.value.phone
                )
                editUserDataUiState.value = editUserDataUiState.value.copy(
                    phoneError = phoneResult.status
                )
                allValidationsPassed.value = phoneResult.status
            }
            is EditUserUIEvent.EditButtonClicked ->{
                editUser()
            }
            is EditUserUIEvent.LogoutButtonClicked -> {
                ToolBar.logout()
            }
            is  EditUserUIEvent.ProfileButtonClicked ->{
                YogaTimeAppRouter.navigateTo(Screen.ClientProfileScreen)
            }
            is  EditUserUIEvent.HomeButtonClicked ->{
                YogaTimeAppRouter.navigateTo(Screen.ClientHomeScreen)
            }

        }
    }

    private fun editUser() {
        var fullNameUser = ToolBar.fullNameId.value
        var emailUser = ToolBar.emailId.value
        var phoneUser = ToolBar.phoneId.value
        if (editUserDataUiState.value.fullName.isNotEmpty()) {
            fullNameUser = editUserDataUiState.value.fullName
        }
        if (editUserDataUiState.value.email.isNotEmpty()) {
            emailUser = editUserDataUiState.value.email
        }
        if (editUserDataUiState.value.phone.isNotEmpty()) {
            phoneUser = editUserDataUiState.value.phone
        }

        if (fullNameUser != null && emailUser != null && phoneUser != null) {
            updateUserInFirebase(
                fullName = fullNameUser,
                email = emailUser,
                phone = phoneUser
            )
        }
    }
    private val TAG = EditUserViewModel::class.simpleName

    private fun updateUserInFirebase(fullName : String, email : String, phone :String) {
        val user = FirebaseAuth.getInstance().currentUser
        val currentEmail = user?.email
        if (!currentEmail.equals(email)) {
            user?.sendEmailVerification()
            user!!.updateEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Inside edit user $email")
                    } else {
                        Log.e(TAG, "Email update failed", task.exception)
                    }
                }
        }
        user?.let {
            val uid = it.uid
            val database = FirebaseDatabase.getInstance()
            val usersReference = database.reference.child("users").child(uid)
            val userMap = hashMapOf(
                "fullName" to fullName,
                "email" to email,
                "phone" to phone,
            )
            var is_Coach = false
            usersReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val userOldData = dataSnapshot.getValue(RegistrationUiState::class.java)
                    if (userOldData != null) {
                        Log.d(TAG, "Inside edit user")
                        is_Coach = when(userOldData.isCoach){
                            false -> { false }
                            true -> { true }
                        }
                        Log.d(TAG, "Inside edit user ==== $userOldData")
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
            usersReference.updateChildren(userMap as Map<String, Any>)
                .addOnSuccessListener {
                    Log.d(TAG, "Inside edit user $is_Coach")
                    when(is_Coach){
                        false ->{YogaTimeAppRouter.navigateTo(Screen.ClientHomeScreen)}
                        true ->{YogaTimeAppRouter.navigateTo(Screen.ManagerHomeScreen)}
                    }

                }
        }
    }
}