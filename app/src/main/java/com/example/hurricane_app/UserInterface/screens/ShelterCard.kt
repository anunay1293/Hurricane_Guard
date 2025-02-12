package com.example.hurricane_app.UserInterface.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hurricane_app.network.ShelterFeature

@Composable
fun ShelterCard(shelter: ShelterFeature) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = shelter.attributes.shelterName ?: "Unknown Shelter",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            shelter.attributes.address?.let {
                Text(text = "📍 Address: $it, ${shelter.attributes.city}, ${shelter.attributes.state} ${shelter.attributes.zip}")
            }
            shelter.attributes.shelterStatus?.let {
                Text(text = "🏠 Status: $it")
            }
            shelter.attributes.totalPopulation?.let {
                Text(text = "👥 Population: $it")
            }
            shelter.attributes.wheelchairAccessible?.let {
                Text(text = "♿ Wheelchair Accessible: ${if (it.trim().isNotEmpty()) "Yes" else "No"}")
            }
            shelter.attributes.petFriendly?.let {
                Text(text = "🐶 Pet Friendly: ${if (it.trim().isNotEmpty()) "Yes" else "No"}")
            }

            // 🔹 Add Coordinates (Latitude & Longitude)
            if (shelter.geometry?.latitude != null && shelter.geometry.longitude != null) {
                Text(text = "📌 Coordinates: ${shelter.geometry.latitude}, ${shelter.geometry.longitude}")
            }
        }
    }
}