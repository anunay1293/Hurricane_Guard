package com.example.hurricane_app.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShelterResponse(
    @SerialName("features") val features: List<ShelterFeature>
)

@Serializable
data class ShelterFeature(
    @SerialName("attributes") val attributes: ShelterAttributes,
    @SerialName("geometry") val geometry: ShelterGeometry? = null
)

@Serializable
data class ShelterAttributes(
    @SerialName("shelter_id") val shelterId: Int?,
    @SerialName("shelter_name") val shelterName: String?,
    @SerialName("address") val address: String?,
    @SerialName("city") val city: String?,
    @SerialName("state") val state: String?,
    @SerialName("zip") val zip: String?,
    @SerialName("shelter_status") val shelterStatus: String?,
    @SerialName("evacuation_capacity") val evacuationCapacity: Int?,
    @SerialName("post_impact_capacity") val postImpactCapacity: Int?,
    @SerialName("total_population") val totalPopulation: Int?,
    @SerialName("org_name") val organizationName: String?,
    @SerialName("wheelchair_accessible") val wheelchairAccessible: String?,
    @SerialName("pet_accommodations_code") val petFriendly: String?
)

@Serializable
data class ShelterGeometry(
    @SerialName("x") val longitude: Double?,
    @SerialName("y") val latitude: Double?
)