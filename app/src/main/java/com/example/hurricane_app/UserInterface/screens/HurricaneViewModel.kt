package com.example.hurricane_app.UserInterface.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.hurricane_app.database.ChecklistDatabase
import com.example.hurricane_app.database.HurricaneEntity
import com.example.hurricane_app.network.HurricaneApi
import com.example.hurricane_app.network.HurricaneForecast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

sealed class HurricaneUiState {
    object Loading : HurricaneUiState()
    data class Success(val forecasts: List<HurricaneForecast>) : HurricaneUiState()
    data class OfflineSuccess(val hurricanes: List<HurricaneEntity>) : HurricaneUiState()
    data class Error(val message: String) : HurricaneUiState()
}

class HurricaneViewModel(application: Application) : AndroidViewModel(application) {

    // Get the hurricane DAO from the same database
    private val hurricaneDao = ChecklistDatabase.getDatabase(application).hurricaneDao()

    private val _hurricaneData = MutableStateFlow<HurricaneUiState>(HurricaneUiState.Loading)
    val hurricaneData: StateFlow<HurricaneUiState> = _hurricaneData

    init {
        fetchHurricaneData()
    }

    private fun fetchHurricaneData() {
        viewModelScope.launch {
            try {
                // Make network call
                val response = HurricaneApi.retrofitService.getHurricaneData()
                val forecasts = response.results
                // Map network results to local entities
                val entities: List<HurricaneEntity> = forecasts.map { forecast ->
                    HurricaneEntity(
                        dateTime = forecast.dateTime,
                        initializedDateTime = forecast.initializedDateTime,
                        latitude = forecast.location.latitude,
                        longitude = forecast.location.longitude,
                        maxWindGustValue = forecast.maxWindGust.value,
                        maxWindGustUnit = forecast.maxWindGust.unit,
                        sustainedWindValue = forecast.sustainedWind.value,
                        sustainedWindUnit = forecast.sustainedWind.unit,
                        status = forecast.status,
                        windowLeftLatitude = forecast.window.left.latitude,
                        windowLeftLongitude = forecast.window.left.longitude,
                        windowRightLatitude = forecast.window.right.latitude,
                        windowRightLongitude = forecast.window.right.longitude
                    )
                }
                // Update database
                hurricaneDao.clearHurricanes()
                hurricaneDao.insertHurricanes(entities)
                _hurricaneData.value = HurricaneUiState.Success(forecasts)
            } catch (e: Exception) {
                // Network failed: load from local database
                hurricaneDao.getAllHurricanes().collect { localHurricanes ->
                    if (localHurricanes.isNotEmpty()) {
                        _hurricaneData.value = HurricaneUiState.OfflineSuccess(localHurricanes)
                    } else {
                        _hurricaneData.value = HurricaneUiState.Error("Failed to load data: ${e.message}")
                    }
                }
            }
        }
    }
}
