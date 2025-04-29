package com.skripsi.airquality.ui.detail

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.skripsi.airquality.R
import com.skripsi.airquality.db.local.Sensor

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val sensorName: TextView = findViewById(R.id.tvSensorName)
        val sensorDesc: TextView = findViewById(R.id.tvSensorDesc)
        val sensorIcon: ImageView = findViewById(R.id.ivSensorIcon)

        val sensor: Sensor? = intent.getParcelableExtra("sensorData") as? Sensor

        sensor?.let {
            sensorName.text = it.name
            sensorDesc.text = it.description
            sensorIcon.setImageResource(it.iconResId)
        }
    }
}
