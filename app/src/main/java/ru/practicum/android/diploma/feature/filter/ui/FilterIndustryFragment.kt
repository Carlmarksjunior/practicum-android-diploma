package ru.practicum.android.diploma.feature.filter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterIndustryBinding
import ru.practicum.android.diploma.feature.filter.presentation.IndustryViewModel
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
            getString(R.string.server_error) -> {
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
                id?.let { industryViewModel.selectIndustry(it, binding.searchInput.text.toString()) }
            }
        )
        binding.industryRecyclerView.adapter = adapter
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
            industryViewModel.saveSelectIndustry()
            findNavController().popBackStack()
        }
        binding.selectButton.setOnClickListener {
            industryViewModel.saveSelectIndustry()
            findNavController().popBackStack()
        }
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

        industryViewModel.init()

        setupListeners()

        industryViewModel.industryScreenState.observe(viewLifecycleOwner) { state ->
            if (state.errorMessage != null) {
                showError(state.errorMessage)
            } else {
                binding.placeholderImage.isVisible = false
                binding.placeholderText.isVisible = false
                binding.industryRecyclerView.isVisible = state.industries.isNotEmpty()
                adapter.updateIndustries(state.industries)
            }
            if (state.selectedButtonView) {
                binding.selectButton.isVisible = state.industries.isNotEmpty()
            } else {
                binding.selectButton.isVisible = false
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
