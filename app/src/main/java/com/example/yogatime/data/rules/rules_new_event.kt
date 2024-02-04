package com.example.yogatime.data.rules

object  rules_new_event {
    fun validatorEventName(EventName : String) : ValidationAddEventResult{
        return ValidationAddEventResult(
            ((!EventName.isNullOrEmpty())&&(EventName.length>3))
        )
    }

    fun validatorEventDate(EventDate : String) : ValidationAddEventResult{
        return ValidationAddEventResult(
            (!EventDate.isNullOrEmpty())
        )
    }

    fun validatorEventTime(EventTime : String) : ValidationAddEventResult{
        return ValidationAddEventResult(
            (!EventTime.isNullOrEmpty())
        )
    }

    fun validatorNumberOfParticipants(NumberOfParticipants : String) : ValidationAddEventResult{
        val allDigit = NumberOfParticipants.all { it.isDigit() }
        return ValidationAddEventResult(
            (!NumberOfParticipants.isNullOrEmpty()&&allDigit&&NumberOfParticipants.length<=3)
        )
    }

}
data class ValidationAddEventResult(
    val status : Boolean  = false
)