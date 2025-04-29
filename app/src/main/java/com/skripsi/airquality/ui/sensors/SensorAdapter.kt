package com.skripsi.airquality.ui.sensors

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skripsi.airquality.databinding.ItemListBinding
import com.skripsi.airquality.db.local.Sensor

class SensorAdapter(private var sensorList: List<Sensor>, private val onItemClick: (Sensor) -> Unit) :
    RecyclerView.Adapter<SensorAdapter.SensorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SensorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SensorViewHolder, position: Int) {
        val sensor = sensorList[position]
        holder.bind(sensor)
    }

    override fun getItemCount(): Int {
        return sensorList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newSensorList: List<Sensor>) {
        this.sensorList = newSensorList
        notifyDataSetChanged()
    }

    inner class SensorViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(sensor: Sensor) {
            binding.tvItemName.text = sensor.name
            binding.tvItemDesc.text = sensor.description
            binding.ivIcon.setImageResource(sensor.iconResId)

            binding.root.setOnClickListener {
                onItemClick(sensor)
            }
        }
    }
}
