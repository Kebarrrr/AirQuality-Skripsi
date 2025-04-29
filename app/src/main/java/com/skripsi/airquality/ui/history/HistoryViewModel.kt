package com.skripsi.airquality.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skripsi.airquality.db.room.SensorData
import com.skripsi.airquality.db.room.SensorDataRepository
import kotlinx.coroutines.launch

class HistoryViewModel(private val repository: SensorDataRepository) : ViewModel() {

    val allSensorData: LiveData<List<SensorData>> = repository.allSensorData

    fun fetchData() {
        viewModelScope.launch {
            repository.fetchAndStoreSensorData()
        }
    }
}
