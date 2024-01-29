package com.example.yogatime.data.EditUser

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.yogatime.data.ToolBar
import com.example.yogatime.data.rules.Validator
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class EditUserViewModel: ViewModel() {

    var editUserDataUiState = mutableStateOf(EditUserUIState())
    var allValidationsPassed = mutableStateOf(false)

    fun onEvent(event : EditUserUIEvent){
        when(event){
            is EditUserUIEvent.fullNameChanged ->{
                editUserDataUiState.value = editUserDataUiState.value.copy(
                    fullName = event.fullName
                )
                val fullNameResult = Validator.validatorFullName(
                    fullName = editUserDataUiState.value.fullName
                )
                editUserDataUiState.value = editUserDataUiState.value.copy(
                    fullNameError = fullNameResult.status
                )
                validateDataWithRules()
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
                validateDataWithRules()
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
                validateDataWithRules()
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

    private fun validateDataWithRules() {
        val fullNameResult = Validator.validatorFullName(
            fullName = editUserDataUiState.value.fullName
        )
        val emailResult = Validator.validatorEmail(
            email = editUserDataUiState.value.email
        )
        val phoneResult = Validator.validatorPhone(
            phone = editUserDataUiState.value.phone
        )
        allValidationsPassed.value = fullNameResult.status &&emailResult.status && phoneResult.status
    }

    private fun editUser() {
        updateUserInFirebase(
            fullName = editUserDataUiState.value.fullName,
            email = editUserDataUiState.value.email,
            phone = editUserDataUiState.value.phone,

        )
    }

    private fun updateUserInFirebase(fullName : String, email : String, phone :String){
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {
            val uid = it.uid
            val database = FirebaseDatabase.getInstance()
            val usersReference = database.reference.child("users").child(uid)

        }
    }
}