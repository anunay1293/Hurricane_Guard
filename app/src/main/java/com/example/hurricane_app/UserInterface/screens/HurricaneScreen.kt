package com.example.hurricane_app.UserInterface.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hurricane_app.database.HurricaneEntity
import com.example.hurricane_app.network.HurricaneForecast
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HurricaneScreen() {
    val hurricaneViewModel: HurricaneViewModel = viewModel()
    val hurricaneData by hurricaneViewModel.hurricaneData.collectAsState()

    var isMapView by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Hurricane Forecast", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { isMapView = !isMapView },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = if (isMapView) "Show List" else "Show Map")
        }
        Spacer(modifier = Modifier.height(8.dp))
        when (hurricaneData) {
            is HurricaneUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Loading data...")
                }
            }
            is HurricaneUiState.Success -> {
                val forecasts = (hurricaneData as HurricaneUiState.Success).forecasts
                if (isMapView) {
                    HurricaneMap(forecasts)
                } else {
                    LazyColumn {
                        itemsIndexed(forecasts) { index, forecast ->
                            HurricaneCard(
                                forecast = forecast,
                                isCurrent = (index == 0)
                            )
                        }
                    }
                }
            }
            is HurricaneUiState.OfflineSuccess -> {
                val hurricanes: List<HurricaneEntity> = (hurricaneData as HurricaneUiState.OfflineSuccess).hurricanes
                Column {
                    // Offline banner
                    Text(
                        text = "Offline Data",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    if (isMapView) {
                        OfflineHurricaneMap(hurricanes)
                    } else {
                        LazyColumn {
                            itemsIndexed(hurricanes) { index, hurricane ->
                                OfflineHurricaneCard(hurricane)
                            }
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
