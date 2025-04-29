package com.skripsi.airquality.ui.sensors

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.skripsi.airquality.R
import com.skripsi.airquality.ui.detail.DetailActivity

class SensorsFragment : Fragment(R.layout.fragment_sensors) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SensorAdapter

    private val sensorsViewModel: SensorsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)

        adapter = SensorAdapter(emptyList()) { sensor ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("sensorData", sensor)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        sensorsViewModel.sensorList.observe(viewLifecycleOwner, Observer { sensors ->
            adapter.updateData(sensors)
        })
    }
}
