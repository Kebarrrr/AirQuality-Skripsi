package com.skripsi.airquality.ui.setting

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.skripsi.airquality.R

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var etCustomPath: EditText
    private lateinit var btnSavePath: Button
    private val settingsViewModel: SettingsViewModel by viewModels()

    private val selectFolderLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri: Uri? = result.data?.data
                uri?.let {
                    val path = uri.path
                    path?.let {
                        settingsViewModel.saveCustomPath(it)
                        etCustomPath.setText(it)
                        Toast.makeText(requireContext(), "Path selected: $path", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etCustomPath = view.findViewById(R.id.etCustomPath)
        btnSavePath = view.findViewById(R.id.btnSavePath)

        settingsViewModel.customPath.observe(viewLifecycleOwner) { currentPath ->
            etCustomPath.setText(currentPath ?: "")
        }

        btnSavePath.setOnClickListener {
            val newPath = etCustomPath.text.toString().trim()
            if (newPath.isNotEmpty()) {
                settingsViewModel.saveCustomPath(newPath)
                Toast.makeText(requireContext(), "Path saved successfully!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please enter a valid path", Toast.LENGTH_SHORT).show()
            }
        }

        etCustomPath.setOnClickListener {
            if (isStoragePermissionGranted()) {
                openStorageForPathSelection()
            } else {
                requestStoragePermission()
            }
        }
    }

    private fun isStoragePermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestStoragePermission() {
        if (!isStoragePermissionGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                STORAGE_PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openStorageForPathSelection()
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun openStorageForPathSelection() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        selectFolderLauncher.launch(intent)
    }

    companion object {
        const val STORAGE_PERMISSION_REQUEST_CODE = 1001
    }
}
