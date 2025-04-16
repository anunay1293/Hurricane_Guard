package com.example.hurricane_app.UserInterface.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.hurricane_app.database.ShelterEntity
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun OfflineShelterMap(shelters: List<ShelterEntity>) {
    val defaultLocation = LatLng(39.0, -95.0)
    val firstShelter = shelters.firstOrNull { it.latitude != null && it.longitude != null }
    val initialLatLng = if (firstShelter?.latitude != null && firstShelter.longitude != null) {
        LatLng(firstShelter.latitude, firstShelter.longitude)
    } else {
        defaultLocation
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialLatLng, 4f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        shelters.forEach { shelter ->
            val lat = shelter.latitude
            val lon = shelter.longitude
            if (lat != null && lon != null) {
                val markerTitle = shelter.shelterName ?: "Unknown Shelter"
                val snippet = listOfNotNull(shelter.address, shelter.city, shelter.state)
                    .joinToString(", ")
                Marker(
                    state = MarkerState(position = LatLng(lat, lon)),
                    title = markerTitle,
                    snippet = snippet
                )
            }
        }
    }
}
