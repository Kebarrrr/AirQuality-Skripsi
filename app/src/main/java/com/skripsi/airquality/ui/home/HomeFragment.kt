package com.skripsi.airquality.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.skripsi.airquality.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi ViewModel
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        // Mengamati nilai NOx
        viewModel.noxValue.observe(viewLifecycleOwner) { value ->
            binding.noxValue.text = value
        }
        viewModel.noxCategory.observe(viewLifecycleOwner) { colorResId ->
            binding.noxCard.setCardBackgroundColor(
                ContextCompat.getColor(requireContext(), colorResId)
            )
        }

        // Mengamati nilai CO2
        viewModel.pm25Value.observe(viewLifecycleOwner) { value ->
            binding.pm25Value.text = value
        }
        viewModel.pm25Category.observe(viewLifecycleOwner) { colorResId ->
            binding.pm25Card.setCardBackgroundColor(
                ContextCompat.getColor(requireContext(), colorResId)
            )
        }

        // Mengamati nilai CO2
        viewModel.pm10Value.observe(viewLifecycleOwner) { value ->
            binding.pm10Value.text = value
        }
        viewModel.pm10Category.observe(viewLifecycleOwner) { colorResId ->
            binding.pm10Card.setCardBackgroundColor(
                ContextCompat.getColor(requireContext(), colorResId)
            )
        }

        // Mengamati nilai CO2
        viewModel.co2Value.observe(viewLifecycleOwner) { value ->
            binding.co2Value.text = value
        }
        viewModel.co2Category.observe(viewLifecycleOwner) { colorResId ->
            binding.co2Card.setCardBackgroundColor(
                ContextCompat.getColor(requireContext(), colorResId)
            )
        }

        // Mengamati nilai NH3
        viewModel.nh3Value.observe(viewLifecycleOwner) { value ->
            binding.nh3Value.text = value
        }
        viewModel.nh3Category.observe(viewLifecycleOwner) { colorResId ->
            binding.nh3Card.setCardBackgroundColor(
                ContextCompat.getColor(requireContext(), colorResId)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
