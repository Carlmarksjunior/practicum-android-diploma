package ru.practicum.android.diploma.feature.filter.ui

import android.os.Bundle
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterLocationBinding
import ru.practicum.android.diploma.feature.filter.presentation.LocationViewModel
import ru.practicum.android.diploma.feature.filter.presentation.state.FilterLocationState
import ru.practicum.android.diploma.util.getNavController

class FilterLocationFragment : Fragment() {

    private var _binding: FragmentFilterLocationBinding? = null
    private val binding get() = _binding!!
    private val locationViewModel: LocationViewModel by viewModel()
    private var countryTextWatcher: TextWatcher? = null
    private var regionTextWatcher: TextWatcher? = null

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
        setupListenersForNavigation()
        setupListeners()
        locationViewModel.getFilters()
        attachObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        countryTextWatcher?.let { binding.countryInput.removeTextChangedListener(it) }
        regionTextWatcher?.let { binding.regionInput.removeTextChangedListener(it) }
        countryTextWatcher = null
        regionTextWatcher = null
        binding.countryAction.setOnClickListener(null)
        binding.regionAction.setOnClickListener(null)
        _binding = null
    }

    private fun setupListenersForNavigation() {
        binding.back.setOnClickListener {
            navigateUp()
        }
        binding.selectButton.setOnClickListener {
            navigateUp()
        }
        binding.countryTextLayout.setOnClickListener {
            navigateToCountry()
        }
        binding.countryInput.setOnClickListener {
            navigateToCountry()
        }
        binding.regionTextLayout.setOnClickListener {
            navigateToRegion()
        }
        binding.regionInput.setOnClickListener {
            navigateToRegion()
        }
    }

    private fun setupListeners() {
        binding.countryAction.setOnClickListener(
            onActionButtonClick(
                binding.countryInput,
                ::navigateToCountry
            ) { locationViewModel.removeCountryFilter() }
        )
        binding.regionAction.setOnClickListener(
            onActionButtonClick(
                binding.regionInput,
                ::navigateToRegion
            ) { locationViewModel.removeRegionFilter() }
        )
        countryTextWatcher = binding.countryInput.addTextChangedListener(
            onTextChanged = onFilterTextChanged(
                binding.countryTextLayout, binding.countryAction
            )
        )
        regionTextWatcher = binding.regionInput.addTextChangedListener(
            onTextChanged = onFilterTextChanged(
                binding.regionTextLayout, binding.regionAction
            )
        )
    }

    private fun attachObservers() {
        locationViewModel.observeLocationState().observe(viewLifecycleOwner) { state ->
            when (state) {
                FilterLocationState.Empty -> {
                    binding.countryInput.setText("")
                    binding.regionInput.setText("")
                    binding.selectButton.isVisible = false
                }
                is FilterLocationState.Success -> {
                    binding.countryInput.setText(state.country)
                    binding.regionInput.setText(state.region)
                    binding.selectButton.isVisible = true
                }
            }
        }
    }

    private fun navigateToCountry() {
        locationViewModel.onNavigate(false)
        requireActivity().getNavController(R.id.containerView)
            .navigate(R.id.action_filterLocationFragment_to_filterCountryFragment)
    }

    private fun navigateToRegion() {
        locationViewModel.onNavigate(true)
        requireActivity().getNavController(R.id.containerView)
            .navigate(R.id.action_filterLocationFragment_to_filterRegionFragment)
    }

    private fun navigateUp() {
        requireActivity().getNavController(R.id.containerView).navigateUp()
    }

    private fun onFilterTextChanged(
        textLayout: TextInputLayout,
        actionButton: ImageButton
    ): (CharSequence?, Int, Int, Int) -> Unit {
        return { text, _, _, _ ->
            if (text.isNullOrEmpty()) {
                textLayout.defaultHintTextColor = ContextCompat.getColorStateList(requireActivity(), R.color.gray)
                actionButton.setImageResource(R.drawable.ic_arrow_forward)
            } else {
                val typedValue = TypedValue()
                requireActivity().theme.resolveAttribute(
                    R.attr.colorOnBackgroundPrimary,
                    typedValue,
                    true
                )
                textLayout.defaultHintTextColor =
                    ContextCompat.getColorStateList(requireActivity(), typedValue.resourceId)
                actionButton.setImageResource(R.drawable.ic_close)
            }
        }
    }

    private fun onActionButtonClick(
        textInput: TextInputEditText,
        navigateAction: () -> Unit,
        removeAction: () -> Unit
    ): View.OnClickListener {
        return {
            if (textInput.text.isNullOrEmpty()) {
                navigateAction.invoke()
            } else {
                removeAction.invoke()
            }
        }
    }
}
