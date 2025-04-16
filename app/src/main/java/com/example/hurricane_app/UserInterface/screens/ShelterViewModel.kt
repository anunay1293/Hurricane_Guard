package com.example.hurricane_app.UserInterface.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.hurricane_app.database.ChecklistDatabase  // Using the same database instance
import com.example.hurricane_app.database.ShelterEntity
import com.example.hurricane_app.network.ShelterApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect

class ShelterViewModel(application: Application) : AndroidViewModel(application) {

    // Get the existing database instance and then the shelterDao
    private val shelterDao = ChecklistDatabase.getDatabase(application).shelterDao()

    private val _shelterUiState = MutableStateFlow<ShelterUiState>(ShelterUiState.Loading)
    val shelterUiState: StateFlow<ShelterUiState> = _shelterUiState

    init {
        fetchShelterData()
    }

    private fun fetchShelterData() {
        viewModelScope.launch {
            try {
                // Attempt to fetch data from network
                val response = ShelterApi.retrofitService.getShelters()
                val features = response.features
                // Convert network results to entities
                val entities: List<ShelterEntity> = features.mapNotNull { feature ->
                    val attributes = feature.attributes
                    attributes.shelterId?.let { id ->
                        ShelterEntity(
                            shelterId = id,
                            shelterName = attributes.shelterName,
                            address = attributes.address,
                            city = attributes.city,
                            state = attributes.state,
                            zip = attributes.zip,
                            shelterStatus = attributes.shelterStatus,
                            evacuationCapacity = attributes.evacuationCapacity,
                            postImpactCapacity = attributes.postImpactCapacity,
                            totalPopulation = attributes.totalPopulation,
                            organizationName = attributes.organizationName,
                            wheelchairAccessible = attributes.wheelchairAccessible,
                            petFriendly = attributes.petFriendly,
                            latitude = feature.geometry?.latitude,
                            longitude = feature.geometry?.longitude
                        )
                    }
                }
                // Update local database by clearing and inserting new data
                shelterDao.clearShelters()
                shelterDao.insertShelters(entities)
                _shelterUiState.value = ShelterUiState.Success(features)
            } catch (e: Exception) {
                // Fallback: Load data from local database if network fails
                shelterDao.getAllShelters().collect { localShelters ->
                    if (localShelters.isNotEmpty()) {
                        _shelterUiState.value = ShelterUiState.OfflineSuccess(localShelters)
                    } else {
                        _shelterUiState.value = ShelterUiState.Error("Failed to load shelters: ${e.message}")
                    }
                }
            }
        }
    }
}









