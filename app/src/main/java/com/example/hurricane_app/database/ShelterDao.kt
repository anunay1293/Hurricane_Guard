package com.example.hurricane_app.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ShelterDao {
    @Query("SELECT * FROM shelters")
    fun getAllShelters(): Flow<List<ShelterEntity>>

    // Using REPLACE strategy to update with new data on conflict
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShelters(shelters: List<ShelterEntity>)

    @Query("DELETE FROM shelters")
    suspend fun clearShelters()
}