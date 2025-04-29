package com.skripsi.airquality.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skripsi.airquality.R
import com.skripsi.airquality.db.api.RetrofitClient
import com.skripsi.airquality.db.room.AppDatabase
import com.skripsi.airquality.db.room.SensorData
import com.skripsi.airquality.db.room.SensorDataRepository
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: SensorDataRepository
    private val sensorDataDao = AppDatabase.getDatabase(application).sensorDataDao()

    // LiveData untuk data sensor terbaru
    private val latestSensorData: LiveData<SensorData>

    // LiveData untuk kategori dan nilai gas
    val pm25Category = MutableLiveData<Int>()
    val pm25Value = MutableLiveData<String>()

    val pm10Category = MutableLiveData<Int>()
    val pm10Value = MutableLiveData<String>()

    val noxCategory = MutableLiveData<Int>()
    val noxValue = MutableLiveData<String>()

    val co2Category = MutableLiveData<Int>()
    val co2Value = MutableLiveData<String>()

    val nh3Category = MutableLiveData<Int>()
    val nh3Value = MutableLiveData<String>()

    init {
        repository = SensorDataRepository(sensorDataDao, RetrofitClient.apiService)
        latestSensorData = repository.getLatestSensorData()

        // Mengamati data sensor terbaru
        latestSensorData.observeForever { sensorData ->
            sensorData?.let {
                // Simpan data ke dalam database lokal
                viewModelScope.launch {
                    sensorDataDao.insert(it) // Simpan data ke database
                }

                // Update nilai dan kategori gas
                updateGasData(it)
            }
        }
    }

    private fun updateGasData(sensorData: SensorData) {
        val nox = sensorData.nox
        val co2 = sensorData.co2
        val nh3 = sensorData.nh3
        val pm25 = sensorData.pm25
        val pm10 = sensorData.pm10

        pm25Value.value = "$pm25 "
        pm25Category.value = getAQICategory("PM2.5", pm25)

        pm10Value.value = "$pm10 "
        pm10Category.value = getAQICategory("PM10", pm10)

        noxValue.value = "$nox "
        noxCategory.value = getAQICategory("NOx", nox)

        co2Value.value = "$co2 "
        co2Category.value = getAQICategory("CO2", co2)

        nh3Value.value = "$nh3 "
        nh3Category.value = getAQICategory("NH3", nh3)
    }

    private fun getAQICategory(gasType: String, value: Double): Int {
        return when (gasType) {

            "PM25" -> {
                when {
                    value <= 12 -> R.color.aqi_good
                    value <= 35 -> R.color.aqi_moderate
                    value <= 55 -> R.color.aqi_unhealthy_sg
                    value <= 150 -> R.color.aqi_unhealthy
                    value <= 250 -> R.color.aqi_very_unhealthy
                    else -> R.color.aqi_hazardous
                }
            }

            "PM10" -> {
                when {
                    value <= 12 -> R.color.aqi_good
                    value <= 35 -> R.color.aqi_moderate
                    value <= 55 -> R.color.aqi_unhealthy_sg
                    value <= 150 -> R.color.aqi_unhealthy
                    value <= 250 -> R.color.aqi_very_unhealthy
                    else -> R.color.aqi_hazardous
                }
            }

            "NOx" -> {
                when {
                    value <= 4 -> R.color.aqi_good
                    value <= 8 -> R.color.aqi_moderate
                    value <= 18 -> R.color.aqi_unhealthy_sg
                    value <= 28 -> R.color.aqi_unhealthy
                    value <= 40 -> R.color.aqi_very_unhealthy
                    else -> R.color.aqi_hazardous
                }
            }
            "CO2" -> {
                when {
                    value <= 600 -> R.color.aqi_good
                    value <= 1000 -> R.color.aqi_moderate
                    value <= 1500 -> R.color.aqi_unhealthy_sg
                    value <= 2000 -> R.color.aqi_unhealthy
                    value <= 5000 -> R.color.aqi_very_unhealthy
                    else -> R.color.aqi_hazardous
                }
            }
            "NH3" -> {
                when {
                    value <= 2 -> R.color.aqi_good
                    value <= 4 -> R.color.aqi_moderate
                    value <= 8 -> R.color.aqi_unhealthy_sg
                    value <= 12 -> R.color.aqi_unhealthy
                    value <= 180 -> R.color.aqi_very_unhealthy
                    else -> R.color.aqi_hazardous
                }
            }
            else -> R.color.aqi_good
        }
    }
}