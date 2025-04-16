package com.example.hurricane_app.UserInterface.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hurricane_app.database.ShelterEntity

@Composable
fun ShelterScreen(
    shelterUiState: ShelterUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    var isMapView by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Shelter Locations", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))

        // Toggle Button
        Button(
            onClick = { isMapView = !isMapView },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(text = if (isMapView) "Show List" else "Show Map")
        }
        Spacer(modifier = Modifier.height(8.dp))

        when (shelterUiState) {
            is ShelterUiState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = "Loading shelters...")
                }
            }
            is ShelterUiState.Success -> {
                // Online data (network model)
                val shelters = shelterUiState.shelters.filterNotNull()
                if (isMapView) {
                    ShelterMap(shelters)
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = contentPadding
                    ) {
                        items(shelters) { shelter ->
                            ShelterCard(shelter)
                        }
                    }
                }
            }
            is ShelterUiState.OfflineSuccess -> {
                // Offline data from local database (ShelterEntity)
                val shelters: List<ShelterEntity> = shelterUiState.shelters
                Column {
                    // Display offline banner
                    Text(
                        text = "Offline Data",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    if (isMapView) {
                        OfflineShelterMap(shelters)
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = contentPadding
                        ) {
                            items(shelters) { shelter ->
                                OfflineShelterCard(shelter)
                            }
                        }
                    }
                }
            }
            is ShelterUiState.Error -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(text = "Error: ${shelterUiState.message}")
                }
            }
        }
    }
}
