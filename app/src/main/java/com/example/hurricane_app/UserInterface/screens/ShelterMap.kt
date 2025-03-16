package com.example.hurricane_app.UserInterface.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.hurricane_app.network.ShelterFeature
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun ShelterMap(shelters: List<ShelterFeature>) {

    val defaultLocation = LatLng(39.0, -95.0)


    val firstShelterWithCoords = shelters.firstOrNull {
        it.geometry?.latitude != null && it.geometry.longitude != null
    }


    val initialLatLng = if (firstShelterWithCoords?.geometry?.latitude != null &&
        firstShelterWithCoords.geometry.longitude != null) {
        LatLng(firstShelterWithCoords.geometry.latitude, firstShelterWithCoords.geometry.longitude)
    } else {
        defaultLocation
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initialLatLng, 4f)
    }

    // Display the map
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {

        shelters.forEach { shelter ->
            val lat = shelter.geometry?.latitude
            val lon = shelter.geometry?.longitude

            if (lat != null && lon != null) {
                val markerTitle = shelter.attributes.shelterName ?: "Unknown Shelter"

                val snippet = buildString {
                    append(shelter.attributes.address ?: "No Address")
                    if (!shelter.attributes.city.isNullOrBlank()) append(", ${shelter.attributes.city}")
                    if (!shelter.attributes.state.isNullOrBlank()) append(", ${shelter.attributes.state}")
                }

                Marker(
                    state = MarkerState(position = LatLng(lat, lon)),
                    title = markerTitle,
                    snippet = snippet
                )
            }
        }
    }
}