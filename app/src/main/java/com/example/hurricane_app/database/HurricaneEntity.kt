package com.example.hurricane_app.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hurricanes")
data class HurricaneEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dateTime: String,
    val initializedDateTime: String,
    val latitude: Double,
    val longitude: Double,
    val maxWindGustValue: Double,
    val maxWindGustUnit: String,
    val sustainedWindValue: Double,
    val sustainedWindUnit: String,
    val status: String,
    val windowLeftLatitude: Double,
    val windowLeftLongitude: Double,
    val windowRightLatitude: Double,
    val windowRightLongitude: Double
)
