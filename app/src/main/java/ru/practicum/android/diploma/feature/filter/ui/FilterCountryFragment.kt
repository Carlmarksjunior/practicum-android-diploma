package ru.practicum.android.diploma.feature.filter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.databinding.FragmentFilterCountryBinding
import ru.practicum.android.diploma.feature.favorite.ui.FavoriteFragment.Companion.SHORT_ITEM_CLICK_DEBOUNCE
import ru.practicum.android.diploma.feature.filter.domain.model.AreaCountry
import ru.practicum.android.diploma.feature.filter.presentation.FilterCountryState
import ru.practicum.android.diploma.feature.filter.presentation.FilterCountryViewModel
import ru.practicum.android.diploma.util.debounce
import kotlin.getValue

class FilterCountryFragment : Fragment() {

    private var _binding: FragmentFilterCountryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FilterCountryViewModel by viewModel()

    @Suppress("LateinitUsage")
    private lateinit var itemClickDebounce: (AreaCountry) -> Unit

    @Suppress("LateinitUsage")
    private lateinit var adapter: CountryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterCountryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.observeState().observe(viewLifecycleOwner) {
            renderState(it)
        }

        itemClickDebounce =
            debounce<AreaCountry>(SHORT_ITEM_CLICK_DEBOUNCE, viewLifecycleOwner.lifecycleScope, false) { country ->
                onItemClick(country)
            }
        adapter = CountryAdapter { country -> itemClickDebounce(country) }
        binding.countriesRecycler.adapter = adapter
        binding.countriesRecycler.layoutManager = layoutManager
        binding.backBtn.setOnClickListener {
            goBack()
        }

        viewModel.loadCountries()
    }

    private fun renderState(state: FilterCountryState) {
        when (state) {
            is FilterCountryState.Loading -> showLoading()
            is FilterCountryState.Error -> showError()
            is FilterCountryState.Content -> {
                showRecycler()
                adapter.submitList(state.countries)
            }
        }
    }

    private fun showRecycler() {
        binding.countriesRecycler.visibility = View.VISIBLE
        binding.placeholderImage.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
    }

    private fun showError() {
        binding.countriesRecycler.visibility = View.GONE
        binding.placeholderImage.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    private fun showLoading() {
        binding.countriesRecycler.visibility = View.GONE
        binding.placeholderImage.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun onItemClick(country: AreaCountry) {
        viewModel.saveCountryFilter(country)
        goBack()
    }

    private fun goBack() {
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
