package com.example.hurricane_app.UserInterface.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.hurricane_app.database.HurricaneEntity
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun OfflineHurricaneMap(hurricanes: List<HurricaneEntity>) {
    val defaultLocation = LatLng(20.0, -80.0)
    val firstHurricane = hurricanes.firstOrNull()
    val initialLatLng = if (firstHurricane != null) {
        LatLng(firstHurricane.latitude, firstHurricane.longitude)
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
        hurricanes.forEach { hurricane ->
            val markerTitle = hurricane.status
            Marker(
                state = MarkerState(position = LatLng(hurricane.latitude, hurricane.longitude)),
                title = markerTitle,
                snippet = "Wind: ${hurricane.sustainedWindValue} ${hurricane.sustainedWindUnit}"
            )
        }
    }
}
