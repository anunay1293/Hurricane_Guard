package com.example.hurricane_app.UserInterface.screens

import  androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.hurricane_app.database.ChecklistItemEntity

@Composable
fun ChecklistDetailScreen(
    checklistId: Int,
    navController: NavController,
    viewModel: ChecklistViewModel = viewModel()
) {
    LaunchedEffect(checklistId) {
        viewModel.setCurrentChecklistId(checklistId)
    }

    val items by viewModel.currentChecklistItems.collectAsState(initial = emptyList())
    var newItemName by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
    ) {
        Text(
            text = "Checklist #$checklistId",
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
                value = newItemName,
                onValueChange = { newItemName = it },
                label = { Text("New Item") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (newItemName.isNotBlank()) {
                        viewModel.addItemToChecklist(checklistId, newItemName)
                        newItemName = ""
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = item.isChecked,
                onCheckedChange = { onToggleChecked(item) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = item.itemName,
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(onClick = { onDeleteItem(item) }) {
                Text("Delete")
            }
        }
    }
}