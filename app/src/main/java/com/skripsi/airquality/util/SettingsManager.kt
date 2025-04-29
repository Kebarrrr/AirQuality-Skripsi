package com.skripsi.airquality.util

import android.content.Context
import android.content.SharedPreferences

class SettingsManager(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("Settings", Context.MODE_PRIVATE)

    fun getCustomPath(): String? {
        return sharedPreferences.getString("custom_path", null)
    }

    fun saveCustomPath(path: String) {
        val editor = sharedPreferences.edit()
        editor.putString("custom_path", path)
        editor.apply()
    }
}
