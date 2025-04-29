package com.skripsi.airquality.db.room

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SensorData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun sensorDataDao(): SensorDataDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Log.d("AppDatabase", "Creating database instance")
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "sensor_data_database"
                ).build()
                INSTANCE = instance
                Log.d("AppDatabase", "Database instance created successfully")
                instance
            }
        }
    }
}
