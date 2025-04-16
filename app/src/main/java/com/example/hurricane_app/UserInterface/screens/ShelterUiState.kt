package com.example.hurricane_app.UserInterface.screens

import com.example.hurricane_app.database.ShelterEntity
import com.example.hurricane_app.network.ShelterFeature

sealed class ShelterUiState {
    object Loading : ShelterUiState()
    // From network call (fresh data)
    data class Success(val shelters: List<ShelterFeature>) : ShelterUiState()
    // Offline data loaded from Room database
    data class OfflineSuccess(val shelters: List<ShelterEntity>) : ShelterUiState()
    data class Error(val message: String) : ShelterUiState()
}