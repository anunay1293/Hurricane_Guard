package com.example.hurricane_app.UserInterface.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.hurricane_app.database.ShelterEntity

@Composable
fun OfflineShelterCard(shelter: ShelterEntity) {
    val context = LocalContext.current
    val latitude = shelter.latitude
    val longitude = shelter.longitude

    val mapsIntent = if (latitude != null && longitude != null) {
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude(${shelter.shelterName})")
        )
    } else null

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(enabled = mapsIntent != null) { mapsIntent?.let(context::startActivity) },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = shelter.shelterName ?: "Unknown Shelter",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            val addressText = listOfNotNull(shelter.address, shelter.city, shelter.state, shelter.zip)
                .joinToString(", ")
            if (addressText.isNotBlank()) {
                Text(text = "📍 Address: $addressText")
            }
            Text(text = "🏠 Status: ${shelter.shelterStatus ?: "N/A"}")
            Text(text = "👥 Population: ${shelter.totalPopulation ?: "N/A"}")
            Text(
                text = "♿ Wheelchair Accessible: ${
                    if ((shelter.wheelchairAccessible ?: "").trim().isNotEmpty()) "Yes" else "No"
                }"
            )
            Text(
                text = "🐶 Pet Friendly: ${
                    if ((shelter.petFriendly ?: "").trim().isNotEmpty()) "Yes" else "No"
                }"
            )
            if (latitude != null && longitude != null) {
                Text(text = "📌 Coordinates: $latitude, $longitude")
                Text(
                    text = "🗺 Open in Google Maps",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { context.startActivity(mapsIntent) }
                )
            }
        }
    }
}
