package ru.practicum.android.diploma.feature.filter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterIndustryBinding
import ru.practicum.android.diploma.feature.filter.presentation.IndustryViewModel
import ru.practicum.android.diploma.feature.filter.presentation.state.IndustryScreenState
import kotlin.getValue

class FilterIndustryFragment : Fragment() {

    private var _binding: FragmentFilterIndustryBinding? = null
    private val binding get() = _binding!!

    private val industryViewModel: IndustryViewModel by viewModel<IndustryViewModel>()

    @Suppress("LateinitUsage")
    private lateinit var adapter: IndustryAdapter

    private fun showError(errorText: String) {
        binding.industryRecyclerView.isVisible = false
        binding.placeholderText.text = errorText
        when (errorText) {
            getString(R.string.server_error), getString(R.string.failed_to_load_regions) -> {
                binding.placeholderImage.setImageResource(R.drawable.img_request_unsuccessful_magic_carpet)
            }
            else -> {
                binding.placeholderImage.setImageResource(R.drawable.img_no_internet_connection)
                binding.placeholderText.text = requireContext().getString(R.string.no_internet)
            }
        }
        binding.placeholderImage.isVisible = true
        binding.placeholderText.isVisible = true

    }
    private fun initRecycler() {
        binding.industryRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

        adapter = IndustryAdapter(listOf(),
            onSelect = { id ->
                id?.let { industryViewModel.selectIndustry(it) }
            }
        )
        binding.industryRecyclerView.adapter = adapter
    }

    private fun renderState(state: IndustryScreenState) {
        if (state.errorMessage == null && state.errorMessageId == null) {
            val hasSelectedVisible = state.visibleIndustries.any { it.isSelected }
            binding.selectButton.isVisible = hasSelectedVisible
            binding.placeholderImage.isVisible = false
            binding.placeholderText.isVisible = false
            binding.industryRecyclerView.isVisible = true
            adapter.updateIndustries(state.visibleIndustries)
        } else {
            val errorMessageFromId = if (state.errorMessageId != null) getString(state.errorMessageId) else ""
            showError(state.errorMessage ?: errorMessageFromId)
        }
    }

    private fun setupListeners() {
        binding.searchInput.doOnTextChanged { text, _, _, _ ->
            val isNotEmpty = !text.isNullOrEmpty()
            binding.searchClearIcon.setImageResource(
                if (isNotEmpty) R.drawable.ic_close else R.drawable.ic_search
            )
            industryViewModel.onSearchTextChanged(text.toString())
        }
        binding.searchClearIcon.setOnClickListener {
            binding.searchInput.text?.clear()
        }
        binding.backButton.setOnClickListener {
            industryViewModel.saveIndustry()
            findNavController().popBackStack()
        }
        binding.selectButton.setOnClickListener {
            industryViewModel.saveIndustry()
            findNavController().popBackStack()
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                industryViewModel.saveIndustry()
                isEnabled = true
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            callback
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterIndustryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        industryViewModel.industryScreenState.observe(viewLifecycleOwner) { state ->
            renderState(state)
        }
        industryViewModel.init()
        setupListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
