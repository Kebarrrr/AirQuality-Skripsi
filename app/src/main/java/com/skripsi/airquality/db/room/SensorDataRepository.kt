package com.skripsi.airquality.db.room

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.skripsi.airquality.db.api.ApiService
import com.skripsi.airquality.db.api.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SensorDataRepository(private val sensorDataDao: SensorDataDao, private val apiService: ApiService) {
    fun getLatestSensorData(): LiveData<SensorData> = sensorDataDao.getLatestSensorData()
    val allSensorData = sensorDataDao.getAllSensorData()
    suspend fun insert(sensorData: SensorData) {
        withContext(Dispatchers.IO) {
            sensorDataDao.insert(sensorData)
        }
    }
    suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            sensorDataDao.deleteAll()
        }
    }

    class FetchSensorDataWorker(
        context: Context,
        workerParams: WorkerParameters
    ) : CoroutineWorker(context, workerParams) {

        override suspend fun doWork(): Result {
            val database = AppDatabase.getDatabase(applicationContext)
            val repository = SensorDataRepository(database.sensorDataDao(), RetrofitClient.apiService)
            repository.fetchAndStoreSensorData()
            return Result.success()
        }
    }

    suspend fun fetchAndStoreSensorData() {
        withContext(Dispatchers.IO) {
            try {
                val response = apiService.getSensorData()
                if (response.isSuccessful) {
                    response.body()?.let { sensorDataList ->
                        sensorDataList.forEach { sensorData ->
                            val isExists = sensorDataDao.isDataExists(sensorData.id)
                            if (!isExists) {
                                sensorDataDao.insert(sensorData)
                            }
                        }
                    }
                } else {
                    Log.e("FETCH_DATA", "Failed: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("FETCH_DATA", "Error: ${e.message}")
            }
        }
    }

}
