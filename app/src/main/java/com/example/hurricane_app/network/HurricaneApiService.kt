package com.example.hurricane_app.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

//  API Base URL
private const val BASE_URL = "https://atlas.microsoft.com/weather/tropical/storms/"

//  Subscription Key
private const val API_KEY = "7L6AI4Eq9cRE7H1g1KSZ5MrVCUY1TEhFxUSBFjgqasStlZV1yqa3JQQJ99BAAC8vTInN6yWzAAAgAZMP4G6P"

//  Retrofit instance with Kotlin Serialization
private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

//  Define API Interface
interface HurricaneApiService {
    @GET("forecasts/json")
    suspend fun getHurricaneData(
        @Query("api-version") apiVersion: String = "1.1",
        @Query("year") year: Int = 2021,
        @Query("basinId") basinId: String = "NP",
        @Query("govId") govId: String = "2",
        @Query("subscription-key") subscriptionKey: String = API_KEY
    ): HurricaneResponse  //  Now returns a structured data class
}

// 🔹 Singleton object to access API
object HurricaneApi {
    val retrofitService: HurricaneApiService by lazy {
        retrofit.create(HurricaneApiService::class.java)
    }
}