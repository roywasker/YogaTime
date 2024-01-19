package com.example.yogatime.data.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.yogatime.data.rules.Validator

class LoginViewModel : ViewModel() {

    var loginUiState = mutableStateOf(LoginUIState())
    var allValidationsPassed = mutableStateOf(false)

    fun onEvent(event : LoginUIEvent) {
        when (event) {
            is LoginUIEvent.EmailChanged -> {
                loginUiState.value = loginUiState.value.copy(
                    email = event.email
                )
            }

            is LoginUIEvent.PasswordChanged -> {
                loginUiState.value = loginUiState.value.copy(
                    password = event.password
                )
            }
            is LoginUIEvent.LoginButtonClicked ->{
                login()
            }
        }
        validateLoginUIDataWithRules()
    }
    private fun validateLoginUIDataWithRules() {

        val emailResult = Validator.validatorEmail(
            email = loginUiState.value.email
        )
        val passwordResult = Validator.validatorPassword(
            password = loginUiState.value.password
        )

        loginUiState.value = loginUiState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )
        allValidationsPassed.value = emailResult.status && passwordResult.status
    }

    private fun login() {}
}