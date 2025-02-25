package com.example.hurricane_app.UserInterface.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hurricane_app.network.HurricaneForecast

@Composable
fun HurricaneScreen() {
    val hurricaneViewModel: HurricaneViewModel = viewModel()
    val hurricaneData by hurricaneViewModel.hurricaneData.collectAsState()

    // Optional: Toggle between list & map
    var isMapView by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Hurricane Forecast", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))

        // Optional button to switch views
        Button(
            onClick = { isMapView = !isMapView },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = if (isMapView) "Show List" else "Show Map")
        }

        Spacer(modifier = Modifier.height(8.dp))

        when (hurricaneData) {
            is HurricaneUiState.Loading -> {
                Text(text = "Loading data...")
            }
            is HurricaneUiState.Success -> {
                val forecasts = (hurricaneData as HurricaneUiState.Success).forecasts

                if (isMapView) {
                    // Show the map with markers
                    HurricaneMap(forecasts)
                } else {
                    // Show the list, labeling the first as current & second as forecast
                    LazyColumn {
                        itemsIndexed(forecasts) { index, forecast ->
                            // Pass a flag to the card telling it which label to use
                            HurricaneCard(
                                forecast = forecast,
                                isCurrent = (index == 0)
                            )
                        }
                    }
                }
            }
            is HurricaneUiState.Error -> {
                Text(text = "Error: ${(hurricaneData as HurricaneUiState.Error).message}")
            }
        }
    }
}