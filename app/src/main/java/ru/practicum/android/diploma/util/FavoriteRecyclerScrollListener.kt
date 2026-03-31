package ru.practicum.android.diploma.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavoriteRecyclerScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val onLoadMore: () -> Unit
) : RecyclerView.OnScrollListener() {

    private var isLoading = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        val shouldLoadMore = !isLoading &&
            firstVisibleItemPosition + visibleItemCount + LOADING_GAP >= totalItemCount

        if (shouldLoadMore) {
            isLoading = true
            onLoadMore.invoke()
        }
    }

    fun setLoading(loading: Boolean) {
        isLoading = loading
    }

    companion object {
        const val LOADING_GAP = 2
    }
}
