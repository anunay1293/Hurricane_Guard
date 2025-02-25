package com.example.hurricane_app.UserInterface.screens
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hurricane_app.network.HurricaneForecast
import com.example.hurricane_app.network.ShelterFeature
@Composable
fun HurricaneCard(
    forecast: HurricaneForecast,
    isCurrent: Boolean
) {
    val titleLabel = if (isCurrent) "Current Position" else "Forecasted Position"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // You can also show forecast.status if you want, e.g. "🌀 Tropical Storm"
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