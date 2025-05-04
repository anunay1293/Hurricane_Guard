package com.example.hurricane_app.UserInterface.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hurricane_app.network.HurricaneForecast

@Composable
fun HurricaneCard(
    forecast: HurricaneForecast,
    isCurrent: Boolean
) {
    val titleLabel = if (isCurrent) "Current Position" else "Forecasted Position"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = titleLabel, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "📅 Date: ${forecast.dateTime}")
            Text(text = "🌍 Location: ${forecast.location.latitude}, ${forecast.location.longitude}")
            Text(text = "💨 Max Wind Gust: ${forecast.maxWindGust.value} ${forecast.maxWindGust.unit}")
            Text(text = "🌬️ Sustained Wind: ${forecast.sustainedWind.value} ${forecast.sustainedWind.unit}")
            Text(text = "📍 Window Left: ${forecast.window.left.latitude}, ${forecast.window.left.longitude}")
            Text(text = "📍 Window Right: ${forecast.window.right.latitude}, ${forecast.window.right.longitude}")
        }
    }
}