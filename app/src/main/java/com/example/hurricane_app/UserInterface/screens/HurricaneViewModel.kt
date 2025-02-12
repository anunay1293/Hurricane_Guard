package com.example.hurricane_app.UserInterface.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hurricane_app.network.HurricaneApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HurricaneViewModel : ViewModel() {
    private val _hurricaneData = MutableStateFlow<String>("Loading...")
    val hurricaneData: StateFlow<String> = _hurricaneData

    init {
        fetchHurricaneData()
    }

    private fun fetchHurricaneData() {
        viewModelScope.launch {
            try {
                _hurricaneData.value = HurricaneApi.retrofitService.getHurricaneData()
            } catch (e: Exception) {
                _hurricaneData.value = "Failed to load data: ${e.message}"
            }
        }
    }
}