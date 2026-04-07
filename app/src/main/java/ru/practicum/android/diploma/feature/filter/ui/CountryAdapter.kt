package ru.practicum.android.diploma.feature.filter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemCountryBinding
import ru.practicum.android.diploma.feature.filter.domain.model.AreaCountry

class CountryAdapter(
    private val onItemClick: (AreaCountry) -> Unit
) : RecyclerView.Adapter<CountryViewHolder>() {

    private var countries = listOf<AreaCountry>()

    /**
     * Используем метод submitList при изменении данных из UI потока
     */
    fun submitList(newList: List<AreaCountry>) {
        val diff = DiffUtil.calculateDiff(CountryDiffCallback(countries, newList))
        countries = newList
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder =
        CountryViewHolder(
            ItemCountryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        holder.bind(country)
        holder.itemView.setOnClickListener {
            onItemClick(country)
        }
    }

    override fun getItemCount(): Int {
        return countries.size
    }
}

class CountryViewHolder(private val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(country: AreaCountry) {
        binding.countryTv.text = country.name
    }
}

class CountryDiffCallback(private val oldList: List<AreaCountry>, private val newList: List<AreaCountry>) :
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
