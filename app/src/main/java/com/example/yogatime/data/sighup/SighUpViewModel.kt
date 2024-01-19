package com.example.yogatime.data.sighup

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.yogatime.data.rules.Validator


class SighUpViewModel : ViewModel() {

    var registrationUiState = mutableStateOf(RegistrationUiState())

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
        }
    }

    private fun singUp() {
        validateDataWithRules()
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

        registrationUiState.value = registrationUiState.value.copy(
            fullNameError = fullNameResult.status,
            emailError = emailResult.status,
            phoneError = phoneResult.status,
            passwordError = passwordResult.status
        )
    }


}