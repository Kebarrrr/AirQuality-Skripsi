package com.skripsi.airquality.ui.setting

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.skripsi.airquality.util.SettingsManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val settingsManager = SettingsManager(application)

    val customPath: MutableLiveData<String?> = MutableLiveData()

    init {

        customPath.value = settingsManager.getCustomPath()
    }

    fun saveCustomPath(path: String) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsManager.saveCustomPath(path)
            customPath.postValue(path)
        }
    }
}
