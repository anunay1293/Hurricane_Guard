package com.example.hurricane_app.UserInterface.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hurricane_app.network.ShelterFeature

@Composable
fun ShelterScreen(
    shelterUiState: ShelterUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {

    var isMapView by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
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

        // Handle different UI states
        when (shelterUiState) {
            is ShelterUiState.Loading -> {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(text = "Loading shelters...")
                }
            }
            is ShelterUiState.Success -> {
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
            is ShelterUiState.Error -> {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text(text = "Error: ${shelterUiState.message}")
                }
            }
        }
    }
}