package ru.practicum.android.diploma.feature.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemVacancyBinding
import ru.practicum.android.diploma.feature.search.domain.model.Salary
import ru.practicum.android.diploma.feature.search.domain.model.Vacancy

class VacanciesAdapter(
    private val onItemClick: (Vacancy) -> Unit,
    private val onLongItemClick: (Vacancy) -> Unit
) : RecyclerView.Adapter<VacancyViewHolder>() {

    private var vacancies = listOf<Vacancy>()

    /**
     * Используем метод submitList при изменении данных из UI потока
     */
    fun submitList(newList: List<Vacancy>) {
        val diff = DiffUtil.calculateDiff(VacancyDiffCallback(vacancies, newList))
        vacancies = newList
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder =
        VacancyViewHolder(
            ItemVacancyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        val vacancy = vacancies[position]
        holder.bind(vacancy)
        holder.itemView.setOnClickListener {
            onItemClick(vacancy)
        }
        holder.itemView.setOnLongClickListener {
            onLongItemClick(vacancy)
            true
        }
    }

    override fun getItemCount(): Int {
        return vacancies.size
    }
}

class VacancyViewHolder(private val binding: ItemVacancyBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(vacancy: Vacancy) {
        val vacancyName = vacancy.name
        val city = vacancy.address?.city
        Glide.with(binding.posterImg.context)
            .load(vacancy.posterUrl)
            .placeholder(R.drawable.ic_placeholder)
            .centerCrop()
            .into(binding.posterImg)
        binding.nameTv.text = if (!city.isNullOrBlank()) {
            "$vacancyName, $city"
        } else {
            vacancyName
        }
        binding.employerTv.text = vacancy.employer?.name ?: "Работодатель не указан"
        binding.salaryTv.text = formatSalary(vacancy.salary)
    }

    private fun formatSalary(salary: Salary?): String {
        if (salary == null) return "Зарплата не указана"
        return salary.formatSalaryInternal()
    }

    private fun Salary.formatSalaryInternal(): String {
        val base = when {
            from != null && to != null -> "от $from до $to"
            from != null -> "от $from"
            to != null -> "до $to"
            else -> return "Зарплата не указана"
        }
        return if (currency != null) "$base $currency" else base
    }
}

class VacancyDiffCallback(private val oldList: List<Vacancy>, private val newList: List<Vacancy>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
