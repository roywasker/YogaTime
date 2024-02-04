package com.example.yogatime.data.Manager



sealed class ManagerHomeUIEvent {
    object LogoutButtonClicked : ManagerHomeUIEvent()
    object ProfileButtonClicked : ManagerHomeUIEvent()

    data class cardClicked(val train: TrainUiState) : ManagerHomeUIEvent()
}