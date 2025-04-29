package com.skripsi.airquality.db.api

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.13:3000/"
    // http://192.168.75.128:3000/ http://192.168.100.174:3000/ http://192.168.1.13:3000/

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    fun checkApiConnection() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.checkHealth()
                if (response.isSuccessful) {
                    Log.d("API_CONNECTION", "Connected: ${response.body()}")
                } else {
                    Log.e("API_CONNECTION", "Failed: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("API_CONNECTION", "Error: ${e.message}")
            }
        }
    }
}
