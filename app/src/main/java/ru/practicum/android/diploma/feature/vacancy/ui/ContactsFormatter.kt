package ru.practicum.android.diploma.feature.vacancy.ui

import android.content.Context
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textview.MaterialTextView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.feature.vacancy.domain.model.Contacts
import ru.practicum.android.diploma.feature.vacancy.domain.model.Vacancy
import ru.practicum.android.diploma.feature.vacancy.presentation.VacancyViewModel
import ru.practicum.android.diploma.feature.vacancy.ui.model.PhoneInfo
import ru.practicum.android.diploma.util.fromDpToPx

class ContactsFormatter(
    private val binding: FragmentVacancyBinding,
    private val vacancyViewModel: VacancyViewModel,
    private val context: Context
) {

    fun setupContacts(vacancy: Vacancy) {
        val contacts = vacancy.contacts
        val isContactsEmpty = contacts == null ||
            contacts.name.isNullOrEmpty() &&
            contacts.email.isNullOrEmpty() &&
            contacts.phones?.isEmpty() == true

        if (isContactsEmpty) {
            binding.contactGroup.visibility = View.GONE
        } else {
            binding.contactGroup.visibility = View.VISIBLE
            displayContactData(contacts)
        }
    }

    private fun displayContactData(contacts: Contacts?) {
        if (contacts == null) return

        setupContactName(contacts.name)
        setupContactEmail(contacts.email)

        if (!contacts.phones.isNullOrEmpty()) {
            val phoneInfos = contacts.phones.map {
                PhoneInfo(it.comment, it.formatted ?: "")
            }
            onPhoneInfoReceived(phoneInfos)
        }
    }

    private fun setupContactName(name: String?) {
        binding.vacancyContactName.apply {
            text = name
            visibility = if (name.isNullOrEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun setupContactEmail(email: String?) {
        binding.vacancyContactEmail.apply {
            text = email
            visibility = if (email.isNullOrEmpty()) View.GONE else View.VISIBLE
            setOnClickListener {
                email?.let { vacancyViewModel.selectEmailClientAndSend(it) }
            }
        }
    }

    private fun onPhoneInfoReceived(phones: List<PhoneInfo>) {
        if (phones.isNotEmpty()) {
            var upperId = binding.vacancyContactEmail.id
            for (phone in phones) {
                if (phone.comment != null) {
                    upperId = initPhoneView(
                        MaterialTextView(getPhoneCommentContextThemeWrapper()),
                        upperId,
                        phone.comment
                    )
                }
                val phoneTextView = MaterialTextView(getPhoneContextThemeWrapper())
                phoneTextView.setOnClickListener { vacancyViewModel.showCallAppsAndDial(phone.phone) }
                upperId = initPhoneView(phoneTextView, upperId, phone.phone)
            }
        }
    }

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

    private fun getPhoneLayoutParams(upperId: Int): ConstraintLayout.LayoutParams =
        ConstraintLayout.LayoutParams(
            ConstraintLayout.LayoutParams.WRAP_CONTENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            topToBottom = upperId
            topMargin = PHONE_VIEW_TOP_MARGIN.fromDpToPx(context)
            startToEnd = binding.start16Line.id
            endToStart = binding.end16Line.id
            horizontalBias = HORIZONTAL_BIAS
        }

    private fun getPhoneContextThemeWrapper() = ContextThemeWrapper(
        context,
        R.style.VacancyContactHighlightTextViewStyle
    )

    private fun getPhoneCommentContextThemeWrapper() = ContextThemeWrapper(
        context,
        R.style.VacancyDetailsSingleTextViewStyle
    )

    companion object {
        private const val PHONE_VIEW_TOP_MARGIN = 8
        private const val HORIZONTAL_BIAS = 0f
    }
}
