package ru.practicum.android.diploma.feature.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.FragmentSearchBinding
import ru.practicum.android.diploma.feature.search.presentation.SearchViewModel
import ru.practicum.android.diploma.feature.search.presentation.model.PagingErrorEvent
import ru.practicum.android.diploma.feature.search.presentation.model.SearchState
import ru.practicum.android.diploma.util.getNavController

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModel<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        val adapter = VacanciesAdapter { vacancy ->
            val action = SearchFragmentDirections.actionSearchFragmentToVacancyFragment(vacancy.id, false)
            requireActivity().getNavController(R.id.containerView).navigate(action)
        }
        binding.searchResultsRecyclerView.adapter = adapter
        observeEvents()
        observeState(adapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeEvents() {
        searchViewModel.observeErrorPagingEvent().observe(viewLifecycleOwner) { error ->
            binding.paginationProgressBarLayout.isVisible = false
            when (error) {
                is PagingErrorEvent.NetworkError -> Toast.makeText(
                    requireContext(),
                    context?.getString(error.message),
                    Toast.LENGTH_SHORT
                ).show()

                is PagingErrorEvent.RequestError -> Toast.makeText(
                    requireContext(),
                    context?.getString(error.message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun observeState(adapter: VacanciesAdapter) {
        searchViewModel.observeSearchState().observe(viewLifecycleOwner) { state ->
            toggleVisibilityWithoutGroup(state)
            toggleVisibilityPlaceholder(state)
            when (state) {
                is SearchState.Result -> {
                    adapter.submitList(state.vacancies)
                    binding.vacanciesCountText.text = context?.getString(R.string.vacancies_n_found, state.founded)
                }

                SearchState.EmptyResultError -> {
                    binding.placeholderImage.setImageResource(R.drawable.img_request_unsuccessful_cat)
                    binding.placeholderText.text = context?.getString(R.string.failed_to_load_vacancies)
                    binding.vacanciesCountText.text = context?.getString(R.string.no_vacancies_found)
                }

                SearchState.Idle -> {
                    binding.placeholderImage.setImageResource(R.drawable.img_search_holder)
                    binding.placeholderImage.isVisible = true
                    binding.searchClearIcon.setImageResource(R.drawable.ic_search)
                }

                SearchState.InputStarted -> {
                    binding.searchClearIcon.setImageResource(R.drawable.ic_close)
                }

                is SearchState.NetworkError -> {
                    binding.placeholderImage.setImageResource(R.drawable.img_no_internet_connection)
                    binding.placeholderText.text = context?.getString(state.message)
                }

                is SearchState.RequestError -> {
                    binding.placeholderImage.setImageResource(R.drawable.img_server_error_entity)
                    binding.placeholderText.text = context?.getString(state.message)
                }

                SearchState.Loading, SearchState.LoadingMore -> {}
            }

        }
    }

    private fun toggleVisibilityWithoutGroup(state: SearchState) {
        binding.mainSearchProgressBar.isVisible = state == SearchState.Loading
        binding.paginationProgressBarLayout.isVisible = state == SearchState.LoadingMore
        binding.vacanciesCountText.isVisible = state is SearchState.Result || state == SearchState.EmptyResultError
        binding.searchResultsRecyclerView.isVisible = state is SearchState.Result || state == SearchState.LoadingMore
    }

    private fun toggleVisibilityPlaceholder(state: SearchState) {
        if (state is SearchState.NetworkError ||
            state is SearchState.RequestError ||
            state == SearchState.EmptyResultError
        ) {
            binding.placeholderImage.isVisible = true
            binding.placeholderText.isVisible = true
        } else {
            binding.placeholderImage.isVisible = false
            binding.placeholderText.isVisible = false
        }
    }

    private fun setupListeners() {
        binding.searchInput.doOnTextChanged { text, _, _, _ ->
            searchViewModel.onSearchTextChanged(text.toString())
        }
        binding.searchClearIcon.setOnClickListener {
            binding.searchInput.text.clear()
        }
        binding.searchResultsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    searchViewModel.onListScroll(
                        (binding.searchResultsRecyclerView.layoutManager as LinearLayoutManager)
                            .findLastVisibleItemPosition()
                    )
                }
            }
        })
    }
}
