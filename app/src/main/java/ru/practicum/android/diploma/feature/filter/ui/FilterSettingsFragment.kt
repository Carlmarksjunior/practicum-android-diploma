package ru.practicum.android.diploma.feature.filter.ui

import android.os.Bundle
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFilterSettingsBinding
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
        binding.workplaceInput.setOnClickListener(onWorkplaceClick())
        binding.workplaceAction.setOnClickListener(onWorkplaceNavigateOrDelete())
        binding.industryTextLayout.setOnClickListener(onIndustryClick())
        binding.industryInput.setOnClickListener(onIndustryClick())
        binding.industryAction.setOnClickListener(onIndustryNavigateOrDelete())
        workplaceTextWatcher = binding.workplaceInput.addTextChangedListener(
            onTextChanged = onTextChanged(binding.workplaceTextLayout, binding.workplaceAction)
        )
        industryTextWatcher = binding.industryInput.addTextChangedListener(
            onTextChanged = onTextChanged(binding.industryTextLayout, binding.industryAction)
        )

        filterSettingsViewModel.init()

        filterSettingsViewModel.filter.observe(viewLifecycleOwner) { state ->
            binding.industryInput.setText(state.industry?.name)
            binding.workplaceInput.setText(state.areaCountry?.name)
            binding.expectedSalaryInput.setText(state.salary?.toString())
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

    private fun onWorkplaceNavigateOrDelete(): View.OnClickListener {
        return {
            Toast.makeText(context, "Workplace navigate/delete", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onIndustryClick(): View.OnClickListener {
        return {
            Toast.makeText(context, "Industry", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onIndustryNavigateOrDelete(): View.OnClickListener {
        return {
            Toast.makeText(context, "Industry navigate/delete", Toast.LENGTH_SHORT).show()
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
