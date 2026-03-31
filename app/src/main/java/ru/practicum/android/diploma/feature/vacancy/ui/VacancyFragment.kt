package ru.practicum.android.diploma.feature.vacancy.ui

import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.text.Html
import android.util.Log
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.feature.vacancy.domain.model.VacancyDetail
import ru.practicum.android.diploma.feature.vacancy.presentation.VacancyState
import ru.practicum.android.diploma.feature.vacancy.presentation.VacancyViewModel
import ru.practicum.android.diploma.feature.vacancy.ui.model.PhoneInfo
import ru.practicum.android.diploma.util.fromDpToPx

class VacancyFragment : Fragment() {

    private val vacancyViewModel by viewModel<VacancyViewModel>()

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    private lateinit var contentVacancy: VacancyDetail



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

        binding.back.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        val idTest = "00072a6b-b4a0-4547-9913-3ffa27cdbf32"
        vacancyViewModel.getVacancyDetail(idTest)
        vacancyViewModel.observeVacancyDetail().observe(viewLifecycleOwner) {
            render(it)
            Log.d("VacancyFragment", "onViewCreated: $it")
        }

        binding.share.setOnClickListener {
            vacancyViewModel.sendVacancyViaMessenger(contentVacancy.url!!)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun render(state: VacancyState) {
        when (state) {
            is VacancyState.Content -> showContent(state)
            is VacancyState.Error -> showError(state)
            is VacancyState.Loading -> showLoading()
        }
    }

    private fun showContent(content: VacancyState.Content) {
        val vacancy = content.content
        contentVacancy = content.content
        with(binding) {
            share.visibility = View.VISIBLE
            addToFavorites.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            vacancyLayout.visibility = View.VISIBLE
            serverError.visibility = View.GONE
            noInternetPlaceHolder.visibility = View.GONE

            vacancyName.text = vacancy.name
            vacancySalary.text = formatSalary(vacancy.salary!!)

            employerName.text = vacancy.employer?.name
            regionCity.text = vacancy.address?.city ?: vacancy.area?.name

            Glide.with(requireContext())
                .load(vacancy.employer?.logo)
                .placeholder(R.drawable.ic_placeholder)
                .into(employerLogo)

            requiredExperience.text = vacancy.experience?.name
            val scheduleText = "${vacancy.employment?.name}, ${vacancy.schedule?.name}"
            employmentSchedule.text = scheduleText

            val rawDescription = vacancy.description
            binding.vacancyDescription.text = rawDescription


            if (vacancy.skills.isNullOrEmpty()) {
                skillsGroup.visibility = View.GONE
            } else {
                skillsGroup.visibility = View.VISIBLE
                vacancySkills.text = vacancy.skills.joinToString("\n") { " • $it" }
            }

            val contacts = vacancy.contacts
            if (contacts == null || (contacts.name.isNullOrEmpty() && contacts.email.isNullOrEmpty() && contacts.phones!!.isEmpty())) {
                contactGroup.visibility = View.GONE
            } else {
                contactGroup.visibility = View.VISIBLE

                if (!contacts.name.isNullOrEmpty()) {
                    vacancyContactName.text = contacts.name
                    vacancyContactName.visibility = View.VISIBLE
                } else {
                    vacancyContactName.visibility = View.GONE
                }

                if (!contacts.email.isNullOrEmpty()) {
                    vacancyContactEmail.text = contacts.email
                    vacancyContactEmail.visibility = View.VISIBLE
                    vacancyContactEmail.setOnClickListener {
                        vacancyViewModel.selectEmailClientAndSend(contacts.email)
                    }
                } else {
                    vacancyContactEmail.visibility = View.GONE
                }

                if (contacts.phones!!.isNotEmpty()) {
                    val phoneInfos = contacts.phones.map {
                        PhoneInfo(it.comment, it.formatted!!)
                    }
                    onPhoneInfoReceived(phoneInfos)
                }
            }
        }
    }

    private fun formatSalary(salary: ru.practicum.android.diploma.feature.vacancy.domain.model.Salary): String {
        if (salary == null || (salary.from == null && salary.to == null)) {
            return getString(R.string.salary_not_specified)
        }

        val text = StringBuilder()

        // Используем ваш метод formatNumber для разделения разрядов пробелами
        salary.from?.let {
            text.append("от ${formatNumber(it)} ")
        }
        salary.to?.let {
            text.append("до ${formatNumber(it)} ")
        }

        val currencySymbol = when (salary.currency) {
            "AZN" -> "₼"
            "BYR" -> "Br"
            "EUR" -> "€"
            "GEL" -> "₾"
            "KGS" -> "с"
            "KZT" -> "₸"
            "RUR" -> "₽"
            "UAH" -> "₴"
            "USD" -> "$"
            "UZS" -> "so'm"
            else -> salary.currency
        }
        text.append(currencySymbol)

        return text.toString()
    }

    private fun formatNumber(number: Int): String {
        return java.text.DecimalFormat("#,###").format(number).replace(",", " ")
    }

    private fun showError(error: VacancyState.Error) {
        binding.progressBar.visibility = View.GONE
        if (error.errorMessage == getString(R.string.no_internet)) {
            binding.share.visibility = View.GONE
            binding.addToFavorites.visibility = View.GONE
            binding.noInternetPlaceHolder.visibility = View.VISIBLE
            binding.serverError.visibility = View.GONE
            binding.vacancyLayout.visibility = View.GONE
        }else{
            binding.share.visibility = View.GONE
            binding.addToFavorites.visibility = View.GONE
            binding.noInternetPlaceHolder.visibility = View.GONE
            binding.serverError.visibility = View.VISIBLE
            binding.vacancyLayout.visibility = View.GONE
        }
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.vacancyLayout.visibility = View.GONE
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
                phoneTextView.setOnClickListener { vacancyViewModel.showCallAppsAndDial(phone.phone) }
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
