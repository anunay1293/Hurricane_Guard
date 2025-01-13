package com.example.hurricane_app.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL =
    "https://gis.fema.gov/arcgis/rest/services/NSS/OpenShelters/MapServer/"

private val retrofit = Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface ShelterApiService {
    @GET("0/query")
    suspend fun getShelters(
        @Query("where") where: String = "1=1",
        @Query("outFields") outFields: String = "*",
        @Query("outSR") outSR: String = "4326",
        @Query("f") format: String = "json"
    ): String
}

object ShelterApi{
val retrofitService: ShelterApiService by lazy {
    retrofit.create(ShelterApiService::class.java)
}
}