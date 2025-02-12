package com.example.hurricane_app.UserInterface.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hurricane_app.network.HurricaneForecast

@Composable
fun HurricaneScreen() {
    val hurricaneViewModel: HurricaneViewModel = viewModel()
    val hurricaneData by hurricaneViewModel.hurricaneData.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Hurricane Forecast", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))

        when (hurricaneData) {
            is HurricaneUiState.Loading -> {
                Text(text = "Loading data...")
            }
            is HurricaneUiState.Success -> {
                val forecasts = (hurricaneData as HurricaneUiState.Success).forecasts
                LazyColumn {
                    items(forecasts) { forecast ->
                        HurricaneCard(forecast)
                    }
                }
            }
            is HurricaneUiState.Error -> {
                Text(text = "Error: ${(hurricaneData as HurricaneUiState.Error).message}")
            }
        }
    }
}