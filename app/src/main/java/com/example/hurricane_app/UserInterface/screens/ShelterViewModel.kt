package com.example.hurricane_app.UserInterface.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hurricane_app.network.ShelterApi
import com.example.hurricane_app.network.ShelterFeature
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShelterViewModel : ViewModel() {

    private val _shelterUiState = MutableStateFlow<ShelterUiState>(ShelterUiState.Loading)
    val shelterUiState: StateFlow<ShelterUiState> = _shelterUiState

    init {
        fetchShelterData()
    }

    private fun fetchShelterData() {
        viewModelScope.launch {
            try {
                val response = ShelterApi.retrofitService.getShelters()
                _shelterUiState.value = ShelterUiState.Success(response.features)
            } catch (e: Exception) {
                _shelterUiState.value = ShelterUiState.Error("Failed to load shelters: ${e.message}")
            }
        }
    }
}











