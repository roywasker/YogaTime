package com.example.yogatime.data.EditUser

data class EditUserUIState (
    var fullName: String = "",
    var email: String = "",
    var phone: String = "",

    var fullNameError: Boolean = true,
    var emailError: Boolean = true,
    var phoneError: Boolean = true,
)