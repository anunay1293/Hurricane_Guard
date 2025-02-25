package com.example.hurricane_app

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hurricane_app.UserInterface.screens.ShelterScreen
import com.example.hurricane_app.UserInterface.screens.ShelterViewModel
import com.example.hurricane_app.UserInterface.screens.ShelterUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShelterDisplay() {
    val shelterViewModel: ShelterViewModel = viewModel()
    val shelterUiState by shelterViewModel.shelterUiState.collectAsState()

    
    Scaffold { innerPadding ->
        ShelterScreen(
            shelterUiState = shelterUiState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = innerPadding,
        )
    }
}
