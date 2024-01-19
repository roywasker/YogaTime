package com.example.yogatime.data.rules

object Validator {

    fun validatorFullName(fullName : String) : ValidationResult{
        val allLetters = fullName.all { it.isLetter() }
        return ValidationResult(
            (!fullName.isNullOrEmpty()&&allLetters)
        )
    }

    fun validatorEmail(email : String) : ValidationResult{
        return ValidationResult(
            (!email.isNullOrEmpty()&&email.length>=7)
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
}

data class ValidationResult(
    val status : Boolean  = false
)