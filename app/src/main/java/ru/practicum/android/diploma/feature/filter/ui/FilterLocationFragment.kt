package ru.practicum.android.diploma.feature.filter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterLocationBinding

class FilterLocationFragment : Fragment() {

    private var _binding: FragmentFilterLocationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navigateToCountryButton.setOnClickListener {
            findNavController().navigate(R.id.action_filterLocationFragment_to_filterCountryFragment)
        }
        binding.navigateToRegionButton.setOnClickListener {
            findNavController().navigate(R.id.action_filterLocationFragment_to_filterRegionFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
