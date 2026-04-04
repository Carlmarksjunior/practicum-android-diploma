package ru.practicum.android.diploma.feature.favorite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentFavoriteBinding
import ru.practicum.android.diploma.feature.favorite.domain.FavoritesState
import ru.practicum.android.diploma.feature.favorite.presentation.FavoriteFragmentViewModel
import ru.practicum.android.diploma.feature.search.ui.VacanciesAdapter
import ru.practicum.android.diploma.feature.vacancy.domain.model.Vacancy
import ru.practicum.android.diploma.util.FavoriteRecyclerScrollListener
import ru.practicum.android.diploma.util.debounce

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteFragmentViewModel by viewModel()

    @Suppress("LateinitUsage")
    private lateinit var itemClickDebounce: (Vacancy) -> Unit
    @Suppress("LateinitUsage")
    private lateinit var adapter: VacanciesAdapter
    @Suppress("LateinitUsage")
    private lateinit var paginationScrollListener: FavoriteRecyclerScrollListener

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
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.observeState().observe(viewLifecycleOwner) {
            renderState(it)
        }

        viewModel.observeToastMessages().observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        itemClickDebounce =
            debounce<Vacancy>(ITEM_CLICK_DEBOUNCE, viewLifecycleOwner.lifecycleScope, false) { vacancy ->
                onItemClick(vacancy)
            }
        adapter = VacanciesAdapter { vacancy -> itemClickDebounce(vacancy) }
        paginationScrollListener = FavoriteRecyclerScrollListener(layoutManager) {
            viewModel.loadNextPage()
        }
        binding.favoriteRecyclerView.adapter = adapter
        binding.favoriteRecyclerView.layoutManager = layoutManager
        binding.favoriteRecyclerView.addOnScrollListener(paginationScrollListener)

        viewModel.loadFirstPage()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.favoriteRecyclerView.clearOnScrollListeners()
        _binding = null
    }

    private fun onItemClick(vacancy: Vacancy) {
        val action = FavoriteFragmentDirections
            .actionFavoriteFragmentToVacancyFragment(vacancy.id, true)
        findNavController().navigate(action)
    }

    private fun renderState(state: FavoritesState) {
        when (state) {
            is FavoritesState.Empty -> showEmptyList()
            is FavoritesState.Error -> showLoadFailed()
            is FavoritesState.Content -> {
                showRecycler()
                adapter.submitList(state.vacancies)
                paginationScrollListener.setLoading(false)
            }
        }
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

    companion object {
        const val ITEM_CLICK_DEBOUNCE = 1000L
    }
}
