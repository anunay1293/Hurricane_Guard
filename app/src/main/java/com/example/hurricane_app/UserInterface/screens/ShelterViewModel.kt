package com.example.hurricane_app.UserInterface.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hurricane_app.network.ShelterApi
import kotlinx.coroutines.launch

sealed interface ShelterUiState{

}
class ShelterViewModel:ViewModel(){
    var shelterUiState: String by mutableStateOf("")
        private set
    init {
        getMarsPhotos()
    }
    fun getMarsPhotos() {
        viewModelScope.launch {
            val listResult = ShelterApi.retrofitService.getShelters()
            shelterUiState=listResult
        }
    }





}












