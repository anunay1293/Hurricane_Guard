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
import androidx.compose.ui.text.style.TextAlign
import com.example.hurricane_app.database.ShelterEntity

@Composable
fun ShelterScreen(
    shelterUiState: ShelterUiState,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    var isMapView by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Shelter Locations",
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

        when (shelterUiState) {
            is ShelterUiState.Loading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Loading shelters...")
            }

            is ShelterUiState.Success -> {
                val shelters = shelterUiState.shelters.filterNotNull()
                if (isMapView) ShelterMap(shelters) else LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = contentPadding
                ) {
                    items(shelters) { ShelterCard(it) }
                }
            }

            is ShelterUiState.OfflineSuccess -> {
                val shelters: List<ShelterEntity> = shelterUiState.shelters
                Column {
                    Text(
                        text = "Offline Data",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    if (isMapView) OfflineShelterMap(shelters) else LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = contentPadding
                    ) {
                        items(shelters) { OfflineShelterCard(it) }
                    }
                }
            }

            is ShelterUiState.Error -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error: ${shelterUiState.message}",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}