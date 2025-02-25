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
    Box(modifier = modifier) {
        ShelterDisplayScreen(shelterUiState, contentPadding)
    }
}
@Composable
fun ShelterDisplayScreen(
    shelterUiState: ShelterUiState,
    contentPadding: PaddingValues
) {
    when (shelterUiState) {
        is ShelterUiState.Loading -> {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text(text = "Loading shelters...")
            }
        }
        is ShelterUiState.Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = contentPadding
            ) {
                items(shelterUiState.shelters.filterNotNull()) { shelter ->
                    ShelterCard(shelter)
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
