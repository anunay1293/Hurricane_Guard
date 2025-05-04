package com.example.hurricane_app.UserInterface.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hurricane_app.database.HurricaneEntity
import com.example.hurricane_app.network.HurricaneForecast

@Composable
fun HurricaneScreen() {
    val hurricaneViewModel: HurricaneViewModel = viewModel()
    val hurricaneData by hurricaneViewModel.hurricaneData.collectAsState()
    var isMapView by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Text(
            text = "Hurricane Forecast",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        Button(
            onClick = { isMapView = !isMapView },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
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
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
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
                val hurricanes: List<HurricaneEntity> =
                    (hurricaneData as HurricaneUiState.OfflineSuccess).hurricanes
                // Offline banner + same layout
                Column {
                    Text(
                        text = "Offline Data",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    if (isMapView) {
                        OfflineHurricaneMap(hurricanes)
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(bottom = 16.dp)
                        ) {
                            itemsIndexed(hurricanes) { index, hurricane ->
                                OfflineHurricaneCard(hurricane)
                            }
                        }
                    }
                }
            }
            is HurricaneUiState.Error -> {
                Text(
                    text = "Error: ${(hurricaneData as HurricaneUiState.Error).message}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}