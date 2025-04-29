package com.skripsi.airquality.ui.sensors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.skripsi.airquality.R
import com.skripsi.airquality.db.local.Sensor

class SensorsViewModel : ViewModel() {

    val sensorList = liveData {
        val sensors = listOf(
            Sensor(
                "MQ135",
                "The MQ-135 Gas sensors are used in air quality control equipments and are suitable for detecting or measuring of NH3, NOx, Alcohol, Benzene, Smoke, CO2. The MQ-135 sensor module comes with a Digital Pin which makes this sensor to operate even without a microcontroller and that comes in handy when you are only trying to detect one particular gas. If you need to measure the gases in PPM, the analog pin need to be used. The analog pin is TTL driven and works on 5V and so can be used with most common microcontrollers.",
                R.drawable.ic_mq135
            ),
            Sensor(
                "PMS5003",
                "PMS5003 is a kind of digital and universal particle concentration sensor,which can be used to obtain the number of suspended particles in the air,i.e. the concentration of particles, and output them in the form of digitalinterface. This sensor can be inserted into variable instruments related tothe concentration of suspended particles in the air or other environmentalimprovement equipments to provide correct concentration data in time.",
                R.drawable.ic_pms5003
            )
        )
        emit(sensors)
    }
}
