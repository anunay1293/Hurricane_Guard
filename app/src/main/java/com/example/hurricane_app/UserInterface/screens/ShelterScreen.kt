package com.example.hurricane_app.UserInterface.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hurricane_app.network.ShelterFeature

@Composable
fun ShelterScreen(
    shelterUiState: ShelterUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    ShelterDisplayScreen(shelterUiState, modifier.padding(top = contentPadding.calculateTopPadding()))
}

/**
 * Displays shelter information based on API response.
 */
@Composable
fun ShelterDisplayScreen(shelterUiState: ShelterUiState, modifier: Modifier = Modifier) {
    when (shelterUiState) {
        is ShelterUiState.Loading -> {
            Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
                Text(text = "Loading shelters...")
            }
        }
        is ShelterUiState.Success -> {
            LazyColumn(modifier = modifier.fillMaxSize().padding(16.dp)) {
                items(shelterUiState.shelters.filterNotNull()) { shelter ->
                    ShelterCard(shelter)
                }
            }
        }
        is ShelterUiState.Error -> {
            Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
                Text(text = "Error: ${shelterUiState.message}")
            }
        }
    }
}