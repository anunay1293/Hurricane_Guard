package com.example.hurricane_app.UserInterface.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hurricane_app.database.ChecklistEntity
import com.example.hurricane_app.navigation.Screen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp



@Composable
fun ChecklistListScreen(
    navController: NavController,
    viewModel: ChecklistViewModel = viewModel()
) {
    val allChecklists by viewModel.allChecklists.collectAsState(initial = emptyList())
    var newChecklistName by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Text(
            text = "Personalized Emergency Checklists",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = newChecklistName,
                onValueChange = { newChecklistName = it },
                label = { Text("New Checklist Name") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (newChecklistName.isNotBlank()) {
                        viewModel.addNewChecklist(newChecklistName)
                        newChecklistName = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(allChecklists) { checklist ->
                ChecklistRow(
                    checklist = checklist,
                    onDelete = { viewModel.deleteChecklist(it) },
                    onOpen = {
                        navController.navigate(Screen.ChecklistDetail.route + "/${it.checklistId}")
                    }
                )
            }
        }
    }
}

@Composable
fun ChecklistRow(
    checklist: ChecklistEntity,
    onDelete: (ChecklistEntity) -> Unit,
    onOpen: (ChecklistEntity) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = checklist.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = { onOpen(checklist) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("Open")
            }
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(onClick = { onDelete(checklist) }) {
                Text("Delete")
            }
        }
    }
}