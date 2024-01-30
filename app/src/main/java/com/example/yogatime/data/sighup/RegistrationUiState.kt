package com.example.yogatime.data.sighup

import android.provider.ContactsContract.CommonDataKinds.Email

data class RegistrationUiState(
    var fullName: String = "",
    var email: String = "",
    var phone: String = "",
    var password: String = "",
    var dateOfBirth: String = "",
    var isCoach: Boolean = false,

    var fullNameError: Boolean = false,
    var emailError: Boolean = false,
    var phoneError: Boolean = false,
    var passwordError: Boolean = false,
    var dateOfBirthError: Boolean = false
)