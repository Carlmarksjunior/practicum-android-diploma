package ru.practicum.android.diploma.feature.filter.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemIndustryBinding

class IndustryAdapter(
    private var industries: List<IndustryUiModel>,
    private val onSelect: (String?) -> Unit
) : RecyclerView.Adapter<IndustryViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemIndustryBinding.inflate(inflater, parent, false)
        return IndustryViewHolder(binding, onSelect)
    }

    override fun getItemCount(): Int {
        return industries.count()
    }

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        holder.bind(industries[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateIndustries(newIndustry: List<IndustryUiModel>) {
        industries = newIndustry
        notifyDataSetChanged()
    }
}

class IndustryViewHolder(
    val binding: ItemIndustryBinding,
    private val onSelect: (String?) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(industry: IndustryUiModel) {
        binding.industryName.text = industry.name
        binding.industrySelect.setImageResource(
            if (industry.isSelected) {
                R.drawable.ic_radio_button_on
            } else {
                R.drawable.ic_radio_button_off
            }
        )

        itemView.setOnClickListener {
            onSelect(industry.id)
        }

    }

}
