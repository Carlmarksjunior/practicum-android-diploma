package ru.practicum.android.diploma.feature.filter.ui

import android.os.Bundle
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterSettingsBinding
import ru.practicum.android.diploma.feature.filter.domain.impl.DeleteFilterByKeyUseCaseImpl.Companion.AREA_COUNTRY_KEY
import ru.practicum.android.diploma.feature.filter.domain.impl.DeleteFilterByKeyUseCaseImpl.Companion.INDUSTRY_KEY
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
        binding.workplaceTextLayout.setOnClickListener(onWorkplaceClick())
        binding.workplaceInput.setOnClickListener {
            findNavController().navigate(R.id.action_filterSettingsFragment_to_filterLocationFragment)
        }
        binding.workplaceAction.setOnClickListener {
            onWorkplaceNavigateOrDelete()
        }
        binding.industryTextLayout.setOnClickListener(onIndustryClick())
        binding.industryInput.setOnClickListener {
            findNavController().navigate(R.id.action_filterSettingsFragment_to_filterIndustryFragment)
        }
        binding.industryAction.setOnClickListener {
            onIndustryNavigateOrDelete()
        }
        workplaceTextWatcher = binding.workplaceInput.addTextChangedListener(
            onTextChanged = onTextChanged(binding.workplaceTextLayout, binding.workplaceAction)
        )
        industryTextWatcher = binding.industryInput.addTextChangedListener(
            onTextChanged = onTextChanged(binding.industryTextLayout, binding.industryAction)
        )

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                isEnabled = true
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            callback
        )

        filterSettingsViewModel.init()

        filterSettingsViewModel.filter.observe(viewLifecycleOwner) { state ->
            if (state.industry?.name != null) {
                binding.industryInput.setText(state.industry.name)
                binding.industryAction.setImageResource(R.drawable.ic_close)
                binding.industryAction.tag = R.drawable.ic_close
            } else {
                binding.industryAction.setImageResource(R.drawable.ic_arrow_forward)
                binding.industryAction.tag = R.drawable.ic_arrow_forward
            }
            if (state.areaCountry?.name != null) {
                binding.workplaceInput.setText(state.areaCountry.name)
                binding.workplaceAction.setImageResource(R.drawable.ic_close)
                binding.workplaceAction.tag = R.drawable.ic_close
            } else {
                binding.workplaceAction.setImageResource(R.drawable.ic_arrow_forward)
                binding.workplaceAction.tag = R.drawable.ic_arrow_forward
            }
            if (state.salary != null) {
                binding.expectedSalaryInput.setText(state.salary.toString())
                binding.expectedSalaryClear.isVisible = true
            } else {
                binding.expectedSalaryClear.isVisible = false
            }
            if (state.isOnlyWithSalary == true) {
                binding.hideWithoutSalaryCheckbox.isChecked = true
            }
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

    private fun onWorkplaceClick(): View.OnClickListener {
        return {
            Toast.makeText(context, "Workplace", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onWorkplaceNavigateOrDelete() {
        if (binding.workplaceAction.tag == R.drawable.ic_arrow_forward) {
            findNavController().navigate(R.id.action_filterSettingsFragment_to_filterLocationFragment)
        } else {
            filterSettingsViewModel.deleteFilter(AREA_COUNTRY_KEY)
        }
    }

    private fun onIndustryClick(): View.OnClickListener {
        return {
            Toast.makeText(context, "Industry", Toast.LENGTH_SHORT).show()
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
}
