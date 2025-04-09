package com.example.hurricane_app.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "checklists")
data class ChecklistEntity(
    @PrimaryKey(autoGenerate = true)
    val checklistId: Int = 0,
    val name: String
)