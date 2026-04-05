package ru.practicum.android.diploma.feature.vacancy.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentVacancyBinding
import ru.practicum.android.diploma.feature.vacancy.domain.model.Vacancy
import ru.practicum.android.diploma.feature.vacancy.presentation.VacancyState
import ru.practicum.android.diploma.feature.vacancy.presentation.VacancyViewModel
import ru.practicum.android.diploma.util.ui.DescriptionFormatterVacancy
import ru.practicum.android.diploma.util.ui.SalaryFormatter

class VacancyFragment : Fragment() {

    private val vacancyViewModel by viewModel<VacancyViewModel>()

    private var _binding: FragmentVacancyBinding? = null
    private val binding get() = _binding!!

    private var contentVacancy: Vacancy? = null

    private val args by navArgs<VacancyFragmentArgs>()

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

        val id = args.vacancyId
        val dbSource = args.dbSource
        if (dbSource) {
            vacancyViewModel.getVacancyFromDatabase(id)
        } else {
            vacancyViewModel.getVacancyDetail(id)
        }

        vacancyViewModel.observeVacancyDetail().observe(viewLifecycleOwner) {
            render(it)
        }

        binding.share.setOnClickListener {
            contentVacancy?.url?.let { url ->
                vacancyViewModel.sendVacancyViaMessenger(url)
            }
        }

        binding.addToFavorites.setOnClickListener {
            vacancyViewModel.handleFavorites(contentVacancy!!)
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
        val isFavorite = content.isFavorite
        contentVacancy = vacancy
        with(binding) {
            placeHolderForEmpty.visibility = View.GONE
            share.visibility = View.VISIBLE
            addToFavorites.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            vacancyLayout.visibility = View.VISIBLE
            serverError.visibility = View.GONE
            noInternetPlaceHolder.visibility = View.GONE
            setupMainInfo(vacancy)
            setIsFavorite(isFavorite)
            setupSkills(vacancy.skills)
            ContactsFormatter(binding, vacancyViewModel, requireContext()).setupContacts(vacancy)
        }
    }

    private fun setupMainInfo(vacancy: Vacancy) {
        with(binding) {
            vacancyName.text = vacancy.name.replace(Regex("\\s+в\\s+\\S+$"), "")
            vacancySalary.text = SalaryFormatter(vacancy.salary, requireContext()).format()

            employerName.text = vacancy.employer?.name
            regionCity.text = vacancy.address?.raw ?: vacancy.area?.name

            Glide.with(requireContext())
                .load(vacancy.employer?.logo)
                .placeholder(R.drawable.ic_placeholder)
                .into(employerLogo)

            requiredExperience.text = vacancy.experience?.name
            val scheduleText = "${vacancy.employment?.name}, ${vacancy.schedule?.name}"
            employmentSchedule.text = scheduleText
            // нужна функция форматирования текста?
            vacancyDescription.text = DescriptionFormatterVacancy(vacancy.description).format()
        }
    }

    private fun setupSkills(skills: List<String>?) {
        if (skills.isNullOrEmpty()) {
            binding.skillsGroup.visibility = View.GONE
        } else {
            binding.skillsGroup.visibility = View.VISIBLE
            binding.vacancySkills.text = skills.joinToString("\n") { " • $it" }
        }
    }

    private fun showError(error: VacancyState.Error) {
        with(binding) {
            progressBar.visibility = View.GONE
            vacancyLayout.visibility = View.GONE
            share.visibility = View.GONE
            addToFavorites.visibility = View.GONE

            noInternetPlaceHolder.visibility = View.GONE
            serverError.visibility = View.GONE
            placeHolderForEmpty.visibility = View.GONE

            when (error.errorMessage) {
                getString(R.string.no_internet) -> {
                    noInternetPlaceHolder.visibility = View.VISIBLE
                }

                getString(R.string.vacancy_not_found) -> {
                    placeHolderForEmpty.visibility = View.VISIBLE
                }

                VacancyViewModel.DATABASE_ERROR -> {
                    placeHolderForEmpty.visibility = View.VISIBLE
                }
                else -> {
                    serverError.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showLoading() {
        with(binding) {
            placeHolderForEmpty.visibility = View.GONE
            serverError.visibility = View.GONE
            noInternetPlaceHolder.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            vacancyLayout.visibility = View.GONE
            share.visibility = View.GONE
            addToFavorites.visibility = View.GONE
        }
    }

    private fun setIsFavorite(isFavorite: Boolean) {
        if (isFavorite) {
            binding.addToFavorites.setImageResource(R.drawable.ic_favorites_on)
        } else {
            binding.addToFavorites.setImageResource(R.drawable.ic_favorites_off)
        }
    }
}
