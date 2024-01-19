package com.example.yogatime.data.sighup

data class RegistrationUiState (
    var fullName : String = "",
    var email : String = "",
    var phone : String = "",
    var password : String = "",

    var fullNameError: Boolean = false,
    var emailError: Boolean = false,
    var phoneError: Boolean = false,
    var passwordError: Boolean = false
)