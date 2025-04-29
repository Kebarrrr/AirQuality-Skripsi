package com.skripsi.airquality.db.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SensorDataDao {
    @Query("SELECT * FROM sensor_data_database ORDER BY timestamp DESC")
    fun getAllSensorData(): LiveData<List<SensorData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sensorData: SensorData)

    @Query("DELETE FROM sensor_data_database")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT 1 FROM sensor_data_database WHERE id = :id)")
    suspend fun isDataExists(id: String): Boolean

    @Query("SELECT * FROM sensor_data_database ORDER BY timestamp DESC LIMIT 1")
    fun getLatestSensorData(): LiveData<SensorData>
}
