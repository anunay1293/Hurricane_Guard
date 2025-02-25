package com.example.hurricane_app.UserInterface.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.hurricane_app.network.HurricaneForecast
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng


@Composable
fun HurricaneMap(forecasts: List<HurricaneForecast>) {
    val defaultLocation = LatLng(20.0, -80.0)
    val firstForecastLocation = forecasts.firstOrNull()?.location
    val initialLatLng = if (firstForecastLocation != null) {
        LatLng(firstForecastLocation.latitude, firstForecastLocation.longitude)
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
        // For each forecast, place a marker. Label first as current, second as forecasted
        forecasts.forEachIndexed { index, forecast ->
            val isCurrent = (index == 0)
            val markerTitle = if (isCurrent) "Current Position" else "Forecasted Position"

            Marker(
                state = MarkerState(
                    position = LatLng(
                        forecast.location.latitude,
                        forecast.location.longitude
                    )
                ),
                title = markerTitle,
                snippet = "Wind: ${forecast.sustainedWind.value} ${forecast.sustainedWind.unit}"
            )
        }
    }
}