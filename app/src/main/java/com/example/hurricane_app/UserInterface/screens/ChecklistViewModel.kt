package com.example.hurricane_app.UserInterface.screens

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.hurricane_app.database.ChecklistDatabase
import com.example.hurricane_app.database.ChecklistEntity
import com.example.hurricane_app.database.ChecklistItemEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class ChecklistViewModel(application: Application) : AndroidViewModel(application) {

    private val db = ChecklistDatabase.getDatabase(application)
    private val dao = db.checklistDao()

    // Flow of all checklists
    val allChecklists = dao.getAllChecklists()

    // Track the currently-open checklistId in a flow
    private val _currentChecklistId = MutableStateFlow<Int?>(null)
    val currentChecklistId: StateFlow<Int?> = _currentChecklistId

    // Whenever currentChecklistId changes, get that checklist’s items
    val currentChecklistItems = _currentChecklistId.flatMapLatest { checklistId ->
        if (checklistId != null) dao.getItemsForChecklist(checklistId)
        else dao.getItemsForChecklist(-1) // empty if no checklist selected
    }

    // -- CHECKLIST METHODS --

    fun addNewChecklist(name: String) = viewModelScope.launch {
        val checklist = ChecklistEntity(name = name)
        dao.insertChecklist(checklist)
    }

    fun deleteChecklist(checklist: ChecklistEntity) = viewModelScope.launch {
        dao.deleteChecklist(checklist)
    }

    fun setCurrentChecklistId(id: Int) {
        _currentChecklistId.value = id
    }

    // -- ITEM METHODS --

    fun addItemToChecklist(checklistId: Int, itemName: String) = viewModelScope.launch {
        val item = ChecklistItemEntity(parentChecklistId = checklistId, itemName = itemName)
        dao.insertItem(item)
    }

    fun toggleItemChecked(item: ChecklistItemEntity) = viewModelScope.launch {
        dao.updateItem(item.copy(isChecked = !item.isChecked))
    }

    fun deleteItem(item: ChecklistItemEntity) = viewModelScope.launch {
        dao.deleteItem(item)
    }
}