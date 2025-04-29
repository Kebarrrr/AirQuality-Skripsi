package com.skripsi.airquality.db.api

import com.skripsi.airquality.db.room.SensorData
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/health")
    suspend fun checkHealth(): Response<Map<String, String>>

    @GET("sensor-data")
    suspend fun getSensorData(): Response<List<SensorData>>
}
