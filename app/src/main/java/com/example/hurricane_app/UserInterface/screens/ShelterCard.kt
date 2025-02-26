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
import com.example.hurricane_app.network.ShelterFeature

@Composable
fun ShelterCard(shelter: ShelterFeature) {
    val context = LocalContext.current


    val latitude = shelter.geometry?.latitude
    val longitude = shelter.geometry?.longitude


    val mapsIntent = if (latitude != null && longitude != null) {
        Intent(Intent.ACTION_VIEW, Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude(${shelter.attributes.shelterName})"))
    } else null

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(enabled = mapsIntent != null) {
                mapsIntent?.let { context.startActivity(it) }
            },
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