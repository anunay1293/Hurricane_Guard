package com.example.hurricane_app.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HurricaneResponse(
    @SerialName("results") val results: List<HurricaneForecast>
)

@Serializable
data class HurricaneForecast(
    @SerialName("dateTime") val dateTime: String,
    @SerialName("initializedDateTime") val initializedDateTime: String,
    @SerialName("location") val location: HurricaneLocation,
    @SerialName("maxWindGust") val maxWindGust: WindData,
    @SerialName("sustainedWind") val sustainedWind: WindData,
    @SerialName("status") val status: String,
    @SerialName("window") val window: HurricaneWindow
)

@Serializable
data class HurricaneLocation(
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double
)

@Serializable
data class WindData(
    @SerialName("value") val value: Double,
    @SerialName("unit") val unit: String
)

@Serializable
data class HurricaneWindow(
    @SerialName("left") val left: HurricaneLocation,
    @SerialName("right") val right: HurricaneLocation
)