package com.example.hurricane_app.UserInterface.screens

import com.example.hurricane_app.network.ShelterFeature

sealed class ShelterUiState {
    object Loading : ShelterUiState()
    data class Success(val shelters: List<ShelterFeature>) : ShelterUiState()
    data class Error(val message: String) : ShelterUiState()
}