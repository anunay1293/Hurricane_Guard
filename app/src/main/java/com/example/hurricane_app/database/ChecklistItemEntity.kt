package com.example.hurricane_app.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "checklist_items",
    foreignKeys = [
        ForeignKey(
            entity = ChecklistEntity::class,
            parentColumns = ["checklistId"],
            childColumns = ["parentChecklistId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("parentChecklistId")]
)
data class ChecklistItemEntity(
    @PrimaryKey(autoGenerate = true)
    val itemId: Int = 0,
    val parentChecklistId: Int, // which checklist this item belongs to
    val itemName: String,
    val isChecked: Boolean = false
)