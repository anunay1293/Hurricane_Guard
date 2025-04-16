package com.example.hurricane_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ChecklistEntity::class, ChecklistItemEntity::class, ShelterEntity::class, HurricaneEntity::class],
    version = 3,
    exportSchema = false
)
abstract class ChecklistDatabase : RoomDatabase() {
    abstract fun checklistDao(): ChecklistDao
    abstract fun shelterDao(): ShelterDao
    abstract fun hurricaneDao(): HurricaneDao

    companion object {
        @Volatile
        private var INSTANCE: ChecklistDatabase? = null

        fun getDatabase(context: Context): ChecklistDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChecklistDatabase::class.java,
                    "checklist_database"
                )

                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
