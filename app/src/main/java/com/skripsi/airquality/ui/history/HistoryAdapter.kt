package com.skripsi.airquality.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.skripsi.airquality.R
import com.skripsi.airquality.db.room.SensorData
import com.skripsi.airquality.db.room.SensorDataDiffCallback
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryAdapter(private var historyList: List<SensorData>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nox: TextView = itemView.findViewById(R.id.text_nox)
        val nh3: TextView = itemView.findViewById(R.id.text_nh3)
        val co2: TextView = itemView.findViewById(R.id.text_co2)
        val pm25: TextView = itemView.findViewById(R.id.text_pm25)
        val pm10: TextView = itemView.findViewById(R.id.text_pm10)
        val timestamp: TextView = itemView.findViewById(R.id.text_timestamp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sensor_data, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = historyList[position]

        holder.nox.text = item.nox.toString()
        holder.nh3.text = item.nh3.toString()
        holder.co2.text = item.co2.toString()
        holder.pm25.text = item.pm25.toString()
        holder.pm10.text = item.pm10.toString()

        val formattedDate = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            .format(Date(item.timestamp))
        holder.timestamp.text = formattedDate
    }

    override fun getItemCount(): Int {
        return historyList.size
    }

    fun updateData(newHistoryList: List<SensorData>) {
        val diffCallback = SensorDataDiffCallback(historyList, newHistoryList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        historyList = newHistoryList
        diffResult.dispatchUpdatesTo(this)
    }

}
