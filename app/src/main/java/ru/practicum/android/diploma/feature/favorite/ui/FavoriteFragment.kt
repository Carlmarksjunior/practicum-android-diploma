package ru.practicum.android.diploma.feature.favorite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ru.practicum.android.diploma.databinding.FragmentFavoriteBinding
import ru.practicum.android.diploma.feature.favorite.presentation.FavoriteFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.feature.search.ui.VacanciesAdapter
import ru.practicum.android.diploma.feature.vacancy.domain.model.VacancyDetail

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteFragmentViewModel by viewModel()

    private lateinit var itemClickDebounce: (VacancyDetail) -> Unit
    private lateinit var adapter: VacanciesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = VacanciesAdapter({ vacancy -> itemClickDebounce(vacancy) }, {})
        binding.favoriteRecyclerView.adapter = adapter
        binding.favoriteRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        /*binding.navigateToVacancyButton.setOnClickListener {
            findNavController().navigate(R.id.action_favoriteFragment_to_vacancyFragment)
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onItemClick(vacancy: VacancyDetail) {
        findNavController().navigate(R.id.action_favoriteFragment_to_vacancyFragment)
    }



    private fun showEmptyList() {
        binding.placeholderImage.setImageResource(R.drawable.img_empty_list)
        binding.placeholderText.text = getString(R.string.empty_list)

        binding.favoriteRecyclerView.visibility = View.GONE
        binding.placeholderImage.visibility = View.VISIBLE
        binding.placeholderText.visibility = View.VISIBLE
    }

    private fun showRecycler() {
        binding.favoriteRecyclerView.visibility = View.VISIBLE
        binding.placeholderImage.visibility = View.GONE
        binding.placeholderText.visibility = View.GONE
    }

    private fun showLoadFailed() {
        binding.placeholderImage.setImageResource(R.drawable.img_request_unsuccessful_cat)
        binding.placeholderText.text = getString(R.string.unsuccessful_query)

        binding.favoriteRecyclerView.visibility = View.GONE
        binding.placeholderImage.visibility = View.VISIBLE
        binding.placeholderText.visibility = View.VISIBLE
    }

}
