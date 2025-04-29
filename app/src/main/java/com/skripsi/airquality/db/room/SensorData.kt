package com.skripsi.airquality.db.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "sensor_data_database")
data class SensorData(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val pm25: Double,
    val pm10: Double,
    val nox: Double,
    val nh3: Double,
    val co2: Double,
    val timestamp: Long = System.currentTimeMillis()
)
