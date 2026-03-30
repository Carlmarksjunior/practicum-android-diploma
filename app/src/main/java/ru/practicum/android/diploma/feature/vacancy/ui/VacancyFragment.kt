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

        val idTest = "026e3822-7106-4332-a39e-35e6dadd6936"
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
            // 1. Управление видимостью основных экранов
            progressBar.visibility = View.GONE
            vacancyLayout.visibility = View.VISIBLE
            serverError.visibility = View.GONE
            noInternetPlaceHolder.visibility = View.GONE

            // Скрываем плейсхолдеры ошибок (если они в отдельных FrameLayout без ID,
            // обычно они управляются через VacancyState.Error)

            // 2. Основная информация
            vacancyName.text = vacancy.name
            vacancySalary.text = formatSalary(vacancy.salary!!)

            // 3. Блок работодателя
            employerName.text = vacancy.employer?.name
            // Используем город из address, если нет - из area
            regionCity.text = vacancy.address?.city ?: vacancy.area?.name

            Glide.with(requireContext())
                .load(vacancy.employer?.logo)
                .placeholder(R.drawable.ic_placeholder)
                .into(employerLogo)

            // 4. Опыт и график
            requiredExperience.text = vacancy.experience?.name
            val scheduleText = "${vacancy.employment?.name}, ${vacancy.schedule?.name}"
            employmentSchedule.text = scheduleText

            // 5. Описание (с поддержкой HTML-тегов)


            val rawDescription = vacancy.description

            val formattedHtml = rawDescription?.let { raw ->
                val blocks = raw.split("\n\n")
                val result = StringBuilder()

                blocks.forEachIndexed { index, block ->
                    val lines = block.split("\n").map { it.trim() }.filter { it.isNotBlank() }
                    if (lines.isEmpty()) return@forEachIndexed

                    val firstLine = lines[0]
                    // Универсальный заголовок: оканчивается на ":" или короткая строка
                    val isHeader = firstLine.endsWith(":")

                    // Добавляем лишний перенос перед заголовком, если это не самый первый блок
                    if (isHeader && index > 0) {
                        result.append("<br><br>")
                    } else if (index > 0) {
                        result.append("<br>")
                    }

                    if (isHeader) {
                        result.append("<strong>$firstLine</strong>")
                        if (lines.size > 1) {
                            val listItems = lines.drop(1).joinToString("<br>") { line ->
                                "&nbsp;&nbsp;•&nbsp;${line.removePrefix("-").removePrefix("•").trim()}"
                            }
                            result.append("<br>$listItems")
                        }
                    } else {
                        if (lines.size > 1) {
                            result.append(lines.joinToString("<br>") { line ->
                                "&nbsp;&nbsp;•&nbsp;${line.removePrefix("-").removePrefix("•").trim()}"
                            })
                        } else {
                            result.append(firstLine)
                        }
                    }
                }
                result.toString()
            }

            binding.vacancyDescription.text = Html.fromHtml(formattedHtml, Html.FROM_HTML_MODE_LEGACY)


            // 6. Ключевые навыки (используем skillsGroup для управления видимостью)
            if (vacancy.skills.isNullOrEmpty()) {
                skillsGroup.visibility = View.GONE
            } else {
                skillsGroup.visibility = View.VISIBLE
                // Форматируем список навыков с буллитами
                vacancySkills.text = vacancy.skills.joinToString("\n") { "• $it" }
            }

            // 7. Контакты (используем contactGroup)
            val contacts = vacancy.contacts
            if (contacts == null || (contacts.name.isNullOrEmpty() && contacts.email.isNullOrEmpty() && contacts.phones!!.isEmpty())) {
                contactGroup.visibility = View.GONE
            } else {
                contactGroup.visibility = View.VISIBLE

                // Имя контактного лица
                if (!contacts.name.isNullOrEmpty()) {
                    vacancyContactName.text = contacts.name
                    vacancyContactName.visibility = View.VISIBLE
                } else {
                    vacancyContactName.visibility = View.GONE
                }

                // Email
                if (!contacts.email.isNullOrEmpty()) {
                    vacancyContactEmail.text = contacts.email
                    vacancyContactEmail.visibility = View.VISIBLE
                    vacancyContactEmail.setOnClickListener {
                        vacancyViewModel.selectEmailClientAndSend(contacts.email)
                    }
                } else {
                    vacancyContactEmail.visibility = View.GONE
                }

                // Обработка телефонов (если у вас есть логика отрисовки списка телефонов)
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
            binding.noInternetPlaceHolder.visibility = View.VISIBLE
            binding.serverError.visibility = View.GONE
            binding.vacancyLayout.visibility = View.GONE
        }else{
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
