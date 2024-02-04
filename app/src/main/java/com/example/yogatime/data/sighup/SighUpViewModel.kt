package com.example.yogatime.data.sighup

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.yogatime.app.YogaTimeApp
import com.example.yogatime.data.rules.Validator
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


class SighUpViewModel : ViewModel() {

    var registrationUiState = mutableStateOf(RegistrationUiState())

    var allValidationsPassed = mutableStateOf(false)

    fun onEvent(event : SignupUIEvent){
        when(event){
            is SignupUIEvent.fullNameChanged ->{
                registrationUiState.value = registrationUiState.value.copy(
                    fullName = event.fullName
                )
                validateDataWithRules()
            }
            is SignupUIEvent.emailChanged ->{
                registrationUiState.value = registrationUiState.value.copy(
                    email = event.email
                )
                validateDataWithRules()
            }
            is SignupUIEvent.phoneChanged ->{
                registrationUiState.value = registrationUiState.value.copy(
                    phone = event.phone
                )
                validateDataWithRules()
            }
            is SignupUIEvent.passwordChanged ->{
                registrationUiState.value = registrationUiState.value.copy(
                    password = event.password
                )
                validateDataWithRules()
            }
            is SignupUIEvent.RegisterButtonClicked ->{
                singUp()
            }
            is SignupUIEvent.dateOfBirthChanged ->{
                registrationUiState.value = registrationUiState.value.copy(
                    dateOfBirth = event.dateOfBirth
                )
                validateDataWithRules()
            }
        }
    }

    private fun singUp() {
        createUserInFirebase(
            fullName = registrationUiState.value.fullName,
            email = registrationUiState.value.email,
            phone = registrationUiState.value.phone,
            password = registrationUiState.value.password,
            dateOfBirth = registrationUiState.value.dateOfBirth
        )
    }

    private fun validateDataWithRules() {
        val fullNameResult = Validator.validatorFullName(
            fullName = registrationUiState.value.fullName
        )
        val emailResult = Validator.validatorEmail(
            email = registrationUiState.value.email
        )
        val phoneResult = Validator.validatorPhone(
            phone = registrationUiState.value.phone
        )
        val passwordResult = Validator.validatorPassword(
            password = registrationUiState.value.password
        )
        val dateOfBirthResult = Validator.validatorDateOfBirth(
            dateOfBirth = registrationUiState.value.dateOfBirth
        )

        registrationUiState.value = registrationUiState.value.copy(
            fullNameError = fullNameResult.status,
            emailError = emailResult.status,
            phoneError = phoneResult.status,
            passwordError = passwordResult.status,
            dateOfBirthError =dateOfBirthResult.status
        )

        allValidationsPassed.value = fullNameResult.status &&emailResult.status && phoneResult.status && passwordResult.status&&dateOfBirthResult.status
    }

    private fun createUserInFirebase(fullName : String, email : String, phone :String, password : String,dateOfBirth : String ){

        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val user = FirebaseAuth.getInstance().currentUser
                    user?.let {
                        val uid = it.uid
                        val database = FirebaseDatabase.getInstance()
                        val usersReference = database.reference.child("users").child(uid)

                        val userMap = hashMapOf(
                            "fullName" to fullName,
                            "email" to email,
                            "phone" to phone,
                            "isCoach" to false,
                            "dateOfBirth" to dateOfBirth
                        )

                        usersReference.setValue(userMap)
                            .addOnSuccessListener {
                                YogaTimeAppRouter.navigateTo(Screen.ClientHomeScreen)
                            }
                    }
                }
            }
    }

}