package com.example.hurricane_app.UserInterface.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.hurricane_app.database.HurricaneEntity
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun OfflineHurricaneMap(hurricanes: List<HurricaneEntity>) {
    val defaultLocation = LatLng(20.0, -80.0)
    val first = hurricanes.firstOrNull()
    val initial = first?.let { LatLng(it.latitude, it.longitude) } ?: defaultLocation

    val cameraState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(initial, 4f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraState
    ) {
        hurricanes.forEach { h ->
            Marker(
                state = MarkerState(LatLng(h.latitude, h.longitude)),
                title = h.status,
                snippet = "Wind: ${h.sustainedWindValue} ${h.sustainedWindUnit}"
            )
        }
    }
}