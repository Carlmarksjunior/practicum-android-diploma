package ru.practicum.android.diploma.feature.filter.ui

import android.os.Bundle
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterSettingsBinding
import ru.practicum.android.diploma.feature.filter.domain.impl.DeleteFilterByKeyUseCaseImpl.Companion.AREA_COUNTRY_KEY
import ru.practicum.android.diploma.feature.filter.domain.impl.DeleteFilterByKeyUseCaseImpl.Companion.AREA_REGION_KEY
import ru.practicum.android.diploma.feature.filter.domain.impl.DeleteFilterByKeyUseCaseImpl.Companion.INDUSTRY_KEY
import ru.practicum.android.diploma.feature.filter.domain.impl.DeleteFilterByKeyUseCaseImpl.Companion.SALARY_KEY
import ru.practicum.android.diploma.feature.filter.domain.model.AreaCountry
import ru.practicum.android.diploma.feature.filter.domain.model.AreaRegion
import ru.practicum.android.diploma.feature.filter.presentation.FilterSettingsViewModel
import kotlin.getValue

class FilterSettingsFragment : Fragment() {

    private var _binging: FragmentFilterSettingsBinding? = null
    private val binding get() = _binging!!
    private var workplaceTextWatcher: TextWatcher? = null
    private var industryTextWatcher: TextWatcher? = null

    private val filterSettingsViewModel: FilterSettingsViewModel by viewModel<FilterSettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binging = FragmentFilterSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigationListeners()
        workplaceTextWatcher = binding.workplaceInput.addTextChangedListener(
            onTextChanged = onTextChanged(binding.workplaceTextLayout, binding.workplaceAction)
        )
        industryTextWatcher = binding.industryInput.addTextChangedListener(
            onTextChanged = onTextChanged(binding.industryTextLayout, binding.industryAction)
        )
        binding.hideWithoutSalaryCheckbox.setOnCheckedChangeListener { _, isChecked ->
            filterSettingsViewModel.setIsOnlyWithSalary(isChecked)
        }

        binding.expectedSalaryClear.setOnClickListener {
            filterSettingsViewModel.deleteFilter(SALARY_KEY)
            binding.expectedSalaryInput.text?.clear()
        }
        binding.expectedSalaryInput.doOnTextChanged { text, _, _, _ ->
            val value = text?.toString()
            if (value?.isEmpty() == true || value?.isBlank() == true) {
                filterSettingsViewModel.deleteFilter(SALARY_KEY)
                binding.expectedSalaryClear.isVisible = false
            }
            val salary = text.toString().toIntOrNull()
            if (salary != null) {
                filterSettingsViewModel.setSalary(salary)
                binding.expectedSalaryClear.isVisible = true
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    isEnabled = true
                    findNavController().popBackStack()
                }
            }
        )
        filterSettingsViewModel.init()
        attachObservers()
        binding.resetButton.setOnClickListener {
            filterSettingsViewModel.clearFilters()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        workplaceTextWatcher?.let { binding.workplaceInput.removeTextChangedListener(it) }
        industryTextWatcher?.let { binding.industryInput.removeTextChangedListener(it) }
        workplaceTextWatcher = null
        industryTextWatcher = null
        _binging = null
    }

    private fun setupNavigationListeners() {
        binding.workplaceInput.setOnClickListener {
            findNavController().navigate(R.id.action_filterSettingsFragment_to_filterLocationFragment)
        }
        binding.workplaceTextLayout.setOnClickListener {
            findNavController().navigate(R.id.action_filterSettingsFragment_to_filterLocationFragment)
        }
        binding.workplaceAction.setOnClickListener {
            onWorkplaceNavigateOrDelete()
        }
        binding.industryInput.setOnClickListener {
            findNavController().navigate(R.id.action_filterSettingsFragment_to_filterIndustryFragment)
        }
        binding.industryTextLayout.setOnClickListener {
            findNavController().navigate(R.id.action_filterSettingsFragment_to_filterIndustryFragment)
        }
        binding.industryAction.setOnClickListener {
            onIndustryNavigateOrDelete()
        }
        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.applyButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun attachObservers() {
        filterSettingsViewModel.filter.observe(viewLifecycleOwner) { state ->

            if (state.industry?.name != null) {
                binding.industryInput.setText(state.industry.name)
            } else {
                binding.industryInput.setText("")
            }
            if (state.areaCountry == null && state.areaRegion == null) {
                binding.workplaceInput.setText("")
            } else {
                binding.workplaceInput.setText(getWorkplaceText(state.areaCountry, state.areaRegion))
            }
            if (state.salary != null) {
                binding.expectedSalaryInput.setText(state.salary.toString())
                binding.expectedSalaryClear.isVisible = true
            } else {
                binding.expectedSalaryInput.text?.clear()
                binding.expectedSalaryClear.isVisible = false
            }
            binding.hideWithoutSalaryCheckbox.isChecked = state.isOnlyWithSalary == true
        }
    }

    private fun getWorkplaceText(areaCountry: AreaCountry?, areaRegion: AreaRegion?): String {
        return if (areaRegion == null) {
            areaCountry!!.name
        } else {
            areaRegion.parentName + ", " + areaRegion.name
        }
    }

    private fun onWorkplaceNavigateOrDelete() {
        if (binding.workplaceAction.tag == R.drawable.ic_arrow_forward) {
            findNavController().navigate(R.id.action_filterSettingsFragment_to_filterLocationFragment)
        } else {
            filterSettingsViewModel.deleteFilter(AREA_COUNTRY_KEY)
            filterSettingsViewModel.deleteFilter(AREA_REGION_KEY)
        }
    }

    private fun onIndustryNavigateOrDelete() {
        if (binding.industryAction.tag == R.drawable.ic_arrow_forward) {
            findNavController().navigate(R.id.action_filterSettingsFragment_to_filterIndustryFragment)
        } else {
            filterSettingsViewModel.deleteFilter(INDUSTRY_KEY)
        }
    }

    private fun onTextChanged(
        textLayout: TextInputLayout,
        actionButton: ImageButton
    ): (CharSequence?, Int, Int, Int) -> Unit {
        return { text, _, _, _ ->
            if (text.isNullOrEmpty()) {
                textLayout.defaultHintTextColor = ContextCompat.getColorStateList(requireActivity(), R.color.gray)
                actionButton.setImageResource(R.drawable.ic_arrow_forward)
                actionButton.tag = R.drawable.ic_arrow_forward
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
                actionButton.tag = R.drawable.ic_close
            }
        }
    }
}
