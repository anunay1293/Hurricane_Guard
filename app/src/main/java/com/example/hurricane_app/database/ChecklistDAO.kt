package com.example.hurricane_app.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ChecklistDao {

    // -- CHECKLIST QUERIES --

    @Query("SELECT * FROM checklists")
    fun getAllChecklists(): Flow<List<ChecklistEntity>>

    @Insert
    suspend fun insertChecklist(checklist: ChecklistEntity): Long

    @Delete
    suspend fun deleteChecklist(checklist: ChecklistEntity)

    // -- ITEM QUERIES --

    @Query("SELECT * FROM checklist_items WHERE parentChecklistId = :checklistId")
    fun getItemsForChecklist(checklistId: Int): Flow<List<ChecklistItemEntity>>

    @Insert
    suspend fun insertItem(item: ChecklistItemEntity): Long

    @Update
    suspend fun updateItem(item: ChecklistItemEntity)

    @Delete
    suspend fun deleteItem(item: ChecklistItemEntity)
}