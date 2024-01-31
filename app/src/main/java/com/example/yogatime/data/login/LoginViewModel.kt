package com.example.yogatime.data.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.yogatime.data.rules.Validator
import com.example.yogatime.navigation.Screen
import com.example.yogatime.navigation.YogaTimeAppRouter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class LoginViewModel : ViewModel() {

    var loginUiState = mutableStateOf(LoginUIState())
    var allValidationsPassed = mutableStateOf(false)
    val popupMessage = mutableStateOf<String?>(null)

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

    private fun login() {
        val email = loginUiState.value.email
        val password = loginUiState.value.password

        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    val user = FirebaseAuth.getInstance().currentUser
                    var isCoach = false
                    val databaseReference = FirebaseDatabase.getInstance().reference
                    val userReference = user?.let { it1 ->
                        databaseReference.child("users").child(it1.uid)
                    }
                    userReference?.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {

                            if (dataSnapshot.exists()) {
                                isCoach = dataSnapshot.child("isCoach").value as Boolean
                                if (isCoach){
                                    YogaTimeAppRouter.navigateTo(Screen.ManagerHomeScreen)
                                }else{
                                    YogaTimeAppRouter.navigateTo(Screen.ClientHomeScreen)
                                }
                            }
                        }
                        override fun onCancelled(databaseError: DatabaseError) {}
                    })
                }else{
                    popupMessage.value = "your email or password is not correct"
                }
            }
    }

}