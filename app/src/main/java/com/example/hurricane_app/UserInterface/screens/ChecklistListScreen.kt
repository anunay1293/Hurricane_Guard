package com.example.hurricane_app.UserInterface.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController


import com.example.hurricane_app.database.ChecklistEntity

import com.example.hurricane_app.navigation.Screen

@Composable
fun ChecklistListScreen(
    navController: NavController,
    viewModel: ChecklistViewModel = viewModel()
) {
    val allChecklists by viewModel.allChecklists.collectAsState(initial = emptyList())

    var newChecklistName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Personalized Emergency Checklists",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Row to add a new checklist
        Row(
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
                }
            ) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // List of existing checklists
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(allChecklists) { checklist ->
                ChecklistRow(
                    checklist = checklist,
                    onDelete = { viewModel.deleteChecklist(it) },
                    onOpen = {
                        // Navigate to detail screen
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
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = checklist.name, style = MaterialTheme.typography.titleMedium)

            Row {
                Button(onClick = { onOpen(checklist) }) {
                    Text("Open")
                }
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedButton(onClick = { onDelete(checklist) }) {
                    Text("Delete")
                }
            }
        }
    }
}