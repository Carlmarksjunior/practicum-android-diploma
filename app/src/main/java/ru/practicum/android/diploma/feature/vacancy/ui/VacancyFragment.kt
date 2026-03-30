package ru.practicum.android.diploma.feature.vacancy.ui

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.textview.MaterialTextView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.feature.vacancy.ui.model.PhoneInfo
import ru.practicum.android.diploma.util.fromDpToPx

class VacancyFragment : Fragment() {

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVacancyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onPhoneInfoReceived(
            listOf(
                PhoneInfo("Комментарий", "89123456789"),
                PhoneInfo(null, "89123456789")
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * @param phones список телефонов с номерами и возможными комментариями
     * upperId - id верхнего View, после которого будет добавлен новый View
     * */
    private fun onPhoneInfoReceived(phones: List<PhoneInfo>) {
        if (phones.isNotEmpty()) {
            var upperId = binding.vacancyContactEmail.id
            for (phone in phones) {
                if (phone.comment != null) {
                    upperId = initPhoneView(
                        MaterialTextView(getPhoneCommentContextThemeWrapper()), upperId, phone.comment
                    )
                }
                val phoneTextView = MaterialTextView(getPhoneContextThemeWrapper())
                phoneTextView.setOnClickListener { /* нажатие на номер телефона */ }
                upperId = initPhoneView(
                    phoneTextView, upperId, phone.phone
                )
            }
        }
    }

    /**
     * @param view View принимающая текст. Либо комментарий, либо номер телефона
     * @param upperId текущий upperId
     * @param text текст на View
     * @return сгенерированный id для View
     */
    private fun initPhoneView(view: TextView, upperId: Int, text: String): Int {
        val generatedId = View.generateViewId()
        view.apply {
            id = generatedId
            this.text = text
            layoutParams = getPhoneLayoutParams(upperId)
        }
        binding.vacancyLayout.addView(view)
        return generatedId
    }

    /**
     * @param upperId текущий upperId
     * @return сгенерированный LayoutParams для View
     * Новая View будет добавлена под View с upperId
     */
    private fun getPhoneLayoutParams(upperId: Int): ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
        ConstraintLayout.LayoutParams.WRAP_CONTENT,
        ConstraintLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        topToBottom = upperId
        topMargin = PHONE_VIEW_TOP_MARGIN.fromDpToPx(requireActivity())
        startToEnd = binding.start16Line.id
        endToStart = binding.end16Line.id
        horizontalBias = HORIZONTAL_BIAS
    }

    private fun getPhoneContextThemeWrapper() = ContextThemeWrapper(
        requireContext(),
        R.style.VacancyContactHighlightTextViewStyle
    )
    private fun getPhoneCommentContextThemeWrapper() =
        ContextThemeWrapper(requireContext(), R.style.VacancyDetailsSingleTextViewStyle)

    companion object {
        private const val PHONE_VIEW_TOP_MARGIN = 8
        private const val HORIZONTAL_BIAS = 0f
    }
}
