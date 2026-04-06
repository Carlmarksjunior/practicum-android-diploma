package ru.practicum.android.diploma.feature.filter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemRegionBinding
import ru.practicum.android.diploma.feature.filter.domain.model.AreaRegion

class RegionAdapter(
    private val onItemClick: (AreaRegion) -> Unit
) : RecyclerView.Adapter<RegionViewHolder>() {

    private var countries = listOf<AreaRegion>()

    /**
     * Используем метод submitList при изменении данных из UI потока
     */
    fun submitList(newList: List<AreaRegion>) {
        val diff = DiffUtil.calculateDiff(RegionDiffCallback(countries, newList))
        countries = newList
        diff.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder =
        RegionViewHolder(
            ItemRegionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
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

class RegionViewHolder(private val binding: ItemRegionBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(country: AreaRegion) {
        binding.regionTv.text = country.name
    }
}

class RegionDiffCallback(private val oldList: List<AreaRegion>, private val newList: List<AreaRegion>) :
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
