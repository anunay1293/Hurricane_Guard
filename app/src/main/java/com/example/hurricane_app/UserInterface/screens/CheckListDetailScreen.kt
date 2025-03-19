package com.example.hurricane_app.UserInterface.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel

import com.example.hurricane_app.database.ChecklistItemEntity

@Composable
fun ChecklistDetailScreen(
    checklistId: Int,
    navController: NavController,
    viewModel: ChecklistViewModel = viewModel()
) {
    // Set the ViewModel’s current checklist
    LaunchedEffect(key1 = checklistId) {
        viewModel.setCurrentChecklistId(checklistId)
    }

    val items by viewModel.currentChecklistItems.collectAsState(initial = emptyList())

    var newItemName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Checklist ID: $checklistId", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))

        // Row to add a new item
        Row {
            OutlinedTextField(
                value = newItemName,
                onValueChange = { newItemName = it },
                label = { Text("New Item") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (newItemName.isNotBlank()) {
                    viewModel.addItemToChecklist(checklistId, newItemName)
                    newItemName = ""
                }
            }) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // List of items in this checklist
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items) { item ->
                ChecklistItemRow(
                    item = item,
                    onToggleChecked = { viewModel.toggleItemChecked(it) },
                    onDeleteItem = { viewModel.deleteItem(it) }
                )
            }
        }
    }
}

@Composable
fun ChecklistItemRow(
    item: ChecklistItemEntity,
    onToggleChecked: (ChecklistItemEntity) -> Unit,
    onDeleteItem: (ChecklistItemEntity) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left: a checkbox + item name
            Row {
                Checkbox(
                    checked = item.isChecked,
                    onCheckedChange = { onToggleChecked(item) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = item.itemName)
            }

            // Right: delete button
            OutlinedButton(onClick = { onDeleteItem(item) }) {
                Text("Delete")
            }
        }
    }
}