package com.example.hurricane_app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.hurricane_app.UserInterface.screens.ShelterScreen
import com.example.hurricane_app.UserInterface.screens.ShelterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShelterDisplay() {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

    ) {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            val shelterViewModel: ShelterViewModel = viewModel()
            ShelterScreen(
                shelterUiState = shelterViewModel.shelterUiState,
                contentPadding = it,
            )
        }
    }
}