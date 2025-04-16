package com.example.hurricane_app.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shelters")
data class ShelterEntity(
    @PrimaryKey
    val shelterId: Int,
    val shelterName: String?,
    val address: String?,
    val city: String?,
    val state: String?,
    val zip: String?,
    val shelterStatus: String?,
    val evacuationCapacity: Int?,
    val postImpactCapacity: Int?,
    val totalPopulation: Int?,
    val organizationName: String?,
    val wheelchairAccessible: String?,
    val petFriendly: String?,
    val latitude: Double?,
    val longitude: Double?
)