package com.example.hurricane_app.UserInterface.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hurricane_app.network.HurricaneApi
import com.example.hurricane_app.network.HurricaneForecast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class HurricaneUiState {
    object Loading : HurricaneUiState()
    data class Success(val forecasts: List<HurricaneForecast>) : HurricaneUiState()
    data class Error(val message: String) : HurricaneUiState()
}

class HurricaneViewModel : ViewModel() {

    private val _hurricaneData = MutableStateFlow<HurricaneUiState>(HurricaneUiState.Loading)
    val hurricaneData: StateFlow<HurricaneUiState> = _hurricaneData

    init {
        fetchHurricaneData()
    }

    private fun fetchHurricaneData() {
        viewModelScope.launch {
            try {
                val response = HurricaneApi.retrofitService.getHurricaneData()
                _hurricaneData.value = HurricaneUiState.Success(response.results)
            } catch (e: Exception) {
                _hurricaneData.value = HurricaneUiState.Error("Failed to load data: ${e.message}")
            }
        }
    }
}