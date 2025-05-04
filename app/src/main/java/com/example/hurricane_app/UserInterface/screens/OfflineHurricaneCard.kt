package com.example.hurricane_app.UserInterface.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hurricane_app.database.HurricaneEntity

@Composable
fun OfflineHurricaneCard(hurricane: HurricaneEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Offline Data", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "📅 Date: ${hurricane.dateTime}")
            Text(text = "🌍 Location: ${hurricane.latitude}, ${hurricane.longitude}")
            Text(text = "💨 Max Wind Gust: ${hurricane.maxWindGustValue} ${hurricane.maxWindGustUnit}")
            Text(text = "🌬️ Sustained Wind: ${hurricane.sustainedWindValue} ${hurricane.sustainedWindUnit}")
            Text(text = "📍 Window Left: ${hurricane.windowLeftLatitude}, ${hurricane.windowLeftLongitude}")
            Text(text = "📍 Window Right: ${hurricane.windowRightLatitude}, ${hurricane.windowRightLongitude}")
        }
    }
}