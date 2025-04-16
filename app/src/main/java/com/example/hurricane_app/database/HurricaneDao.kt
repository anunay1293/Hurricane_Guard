package com.example.hurricane_app.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HurricaneDao {
    @Query("SELECT * FROM hurricanes ORDER BY id DESC")
    fun getAllHurricanes(): Flow<List<HurricaneEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHurricanes(hurricanes: List<HurricaneEntity>)

    @Query("DELETE FROM hurricanes")
    suspend fun clearHurricanes()
}
