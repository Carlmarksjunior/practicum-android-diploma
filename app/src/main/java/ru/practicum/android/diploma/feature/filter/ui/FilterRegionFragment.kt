package ru.practicum.android.diploma.feature.filter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterRegionBinding
import ru.practicum.android.diploma.feature.filter.presentation.FilterRegionViewmodel
import ru.practicum.android.diploma.feature.filter.presentation.state.FilterRegionState

class FilterRegionFragment : Fragment() {

    private val filterViewModel: FilterRegionViewmodel by viewModel()

    private var _binding: FragmentFilterRegionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterRegionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filterViewModel.getRegions()
        setupListeners()
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        filterViewModel.observeRegionLiveData().observe(viewLifecycleOwner) {
            render(it)
        }

        filterViewModel.observeSaveLiveData().observe(viewLifecycleOwner) {
            findNavController().popBackStack()
        }
    }

    private fun render(state: FilterRegionState) {
        when (state) {
            is FilterRegionState.Content -> showContent(state)
            is FilterRegionState.Error -> showError(state)
            is FilterRegionState.Loading -> showLoading()
        }
    }

    private fun showContent(state: FilterRegionState.Content) {
        if (state.regions.isNotEmpty()) {
            binding.searchResultsRecyclerView.visibility = View.VISIBLE
            binding.placeholderImage.visibility = View.GONE
            binding.placeholderText.visibility = View.GONE
            binding.mainSearchProgressBar.visibility = View.GONE

            val adapter = RegionAdapter { filterViewModel.saveRegion(it) }
            adapter.submitList(state.regions)
            binding.searchResultsRecyclerView.adapter = adapter
            binding.searchResultsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        } else {
            binding.searchResultsRecyclerView.visibility = View.GONE
            binding.placeholderImage.visibility = View.VISIBLE
            binding.placeholderText.visibility = View.VISIBLE
            binding.mainSearchProgressBar.visibility = View.GONE

            binding.placeholderImage.setImageResource(R.drawable.img_request_unsuccessful_cat)
            binding.placeholderText.text = getString(R.string.region_not_found)
        }

    }

    private fun showError(state: FilterRegionState.Error) {
        binding.searchResultsRecyclerView.visibility = View.GONE
        binding.placeholderImage.visibility = View.VISIBLE
        binding.placeholderText.visibility = View.VISIBLE
        binding.mainSearchProgressBar.visibility = View.GONE

        binding.placeholderImage.setImageResource(R.drawable.img_request_unsuccessful_magic_carpet)
        binding.placeholderText.text = getString(R.string.failed_to_load_regions)

    }

    private fun showLoading() {
        binding.searchResultsRecyclerView.visibility = View.GONE
        binding.placeholderImage.visibility = View.GONE
        binding.placeholderText.visibility = View.GONE
        binding.mainSearchProgressBar.visibility = View.VISIBLE
    }

    private fun setupListeners() {
        binding.searchInput.doOnTextChanged { text, _, _, _ ->
            filterViewModel.onSearchTextChanged(text.toString())
            if (text!!.isEmpty()) {
                binding.searchClearIcon.setImageResource(R.drawable.ic_search)
            } else {
                binding.searchClearIcon.setImageResource(R.drawable.ic_close)
            }
        }
        binding.searchClearIcon.setOnClickListener {
            binding.searchInput.text.clear()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
