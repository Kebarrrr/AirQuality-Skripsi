package com.skripsi.airquality.ui.history

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.skripsi.airquality.databinding.FragmentHistoryBinding
import com.skripsi.airquality.db.api.RetrofitClient
import com.skripsi.airquality.db.room.AppDatabase
import com.skripsi.airquality.db.room.SensorData
import com.skripsi.airquality.db.room.SensorDataRepository
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var historyViewModel: HistoryViewModel

    private val requestStoragePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                generatePdf()
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val database = AppDatabase.getDatabase(requireContext())
        val repository = SensorDataRepository(database.sensorDataDao(), RetrofitClient.apiService)

        val fetchDataRequest =
            PeriodicWorkRequestBuilder<SensorDataRepository.FetchSensorDataWorker>(1, TimeUnit.MINUTES)
                .build()
        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            "FetchSensorData",
            ExistingPeriodicWorkPolicy.KEEP,
            fetchDataRequest
        )

        val viewModelFactory = HistoryViewModelFactory(repository)
        historyViewModel = ViewModelProvider(this, viewModelFactory)[HistoryViewModel::class.java]

        historyAdapter = HistoryAdapter(emptyList())
        binding.recyclerViewHistory.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewHistory.adapter = historyAdapter

        historyViewModel.allSensorData.observe(viewLifecycleOwner) { historyList ->
            historyAdapter.updateData(historyList)
            if (historyList.isEmpty()) {
                Log.d("HistoryFragment", "Database is empty, no data to display.")
            } else {
                Log.d("HistoryFragment", "Data fetched: $historyList")
            }
        }

        historyViewModel.fetchData()

        RetrofitClient.checkApiConnection()

        binding.btnDownload.setOnClickListener {
            checkPermissionAndGeneratePdf()
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun checkPermissionAndGeneratePdf() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            generatePdf()
        } else {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                generatePdf()
            } else {
                requestStoragePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    private fun generatePdf() {
        val historyList = historyViewModel.allSensorData.value
        if (historyList.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "No data available to generate PDF.", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val contentResolver = requireContext().contentResolver
            val contentValues = android.content.ContentValues().apply {
                put(android.provider.MediaStore.MediaColumns.DISPLAY_NAME, "sensor_data_history.pdf")
                put(android.provider.MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                put(android.provider.MediaStore.MediaColumns.RELATIVE_PATH, "Documents/AirQualityApp")
            }

            val uri = contentResolver.insert(android.provider.MediaStore.Files.getContentUri("external"), contentValues)
            if (uri != null) {
                contentResolver.openOutputStream(uri)?.use { outputStream ->
                    writePdfContent(outputStream, historyList)
                }
                Toast.makeText(requireContext(), "PDF successfully generated!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Failed to create PDF file.", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(requireContext(), "Failed to generate PDF.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun writePdfContent(outputStream: OutputStream, historyList: List<SensorData>) {
        val document = Document()
        PdfWriter.getInstance(document, outputStream)
        document.open()

        document.add(Paragraph("Sensor Data History"))
        document.add(Paragraph("\n"))

        val table = PdfPTable(5)
        table.widthPercentage = 100f
        table.setWidths(intArrayOf(2, 2, 2, 2, 3))

        val headers = listOf("No", "NOx", "NH3", "CO2", "Timestamp")
        headers.forEach { header ->
            val cell = PdfPCell(Paragraph(header))
            cell.horizontalAlignment = Element.ALIGN_CENTER
            table.addCell(cell)
        }

        var index = 1
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        historyList.forEach { data ->
            table.addCell(PdfPCell(Paragraph(index.toString())).apply { horizontalAlignment = Element.ALIGN_CENTER })
            table.addCell(PdfPCell(Paragraph(data.nox.toString())).apply { horizontalAlignment = Element.ALIGN_CENTER })
            table.addCell(PdfPCell(Paragraph(data.nh3.toString())).apply { horizontalAlignment = Element.ALIGN_CENTER })
            table.addCell(PdfPCell(Paragraph(data.co2.toString())).apply { horizontalAlignment = Element.ALIGN_CENTER })
            table.addCell(PdfPCell(Paragraph(data.pm25.toString())).apply { horizontalAlignment = Element.ALIGN_CENTER })
            table.addCell(PdfPCell(Paragraph(data.pm10.toString())).apply { horizontalAlignment = Element.ALIGN_CENTER })
            val formattedDate = dateFormatter.format(Date(data.timestamp))
            table.addCell(PdfPCell(Paragraph(formattedDate)).apply { horizontalAlignment = Element.ALIGN_CENTER })
            index++
        }
        document.add(table)
        document.close()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
