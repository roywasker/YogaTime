package com.example.yogatime.data.rules

import android.util.Patterns

object Validator {

    fun validatorFullName(fullName : String) : ValidationResult{
        return ValidationResult(
            (!fullName.isNullOrEmpty())
        )
    }

    fun validatorEmail(email : String) : ValidationResult{
        return ValidationResult(
            email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()

        )
    }

    fun validatorPhone(phone : String) : ValidationResult{
        val allDigit = phone.all { it.isDigit() }
        return ValidationResult(
            (!phone.isNullOrEmpty()&&allDigit&&phone.length==10)
        )
    }

    fun validatorPassword(password : String) : ValidationResult{
        return ValidationResult(
            (!password.isNullOrEmpty() && password.length>=6)
        )
    }

    fun validatorDateOfBirth(dateOfBirth : String) : ValidationResult{
        return ValidationResult(
            (!dateOfBirth.isNullOrEmpty())
        )
    }
}

data class ValidationResult(
    val status : Boolean  = false
)