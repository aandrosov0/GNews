package aandrosov.gnews.ui.fragments

import aandrosov.gnews.R
import aandrosov.gnews.ui.adapters.NewsAdapter
import aandrosov.gnews.ui.utils.getSystemLanguage
import aandrosov.gnews.ui.utils.openWebPage
import aandrosov.gnews.ui.utils.showMessage
import aandrosov.gnews.ui.viewModels.NewsViewModel
import aandrosov.gnews.ui.viewModels.ViewModelFactory
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class NewsFragment : Fragment(R.layout.news_fragment), SearchView.OnQueryTextListener {
    private val newsViewModel: NewsViewModel by viewModels { ViewModelFactory }
    private val newsAdapter = NewsAdapter { requireContext().openWebPage(it.url) }
    private lateinit var progressBarLayout: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressBarLayout = view.requireViewById<ViewGroup>(R.id.progress_bar_layout)

        val newsRecyclerView = view.requireViewById<RecyclerView>(R.id.news_recycler_view)
        newsRecyclerView.adapter = newsAdapter

        val searchView = view.requireViewById<SearchView>(R.id.search_view)
        searchView.setOnQueryTextListener(this)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsViewModel.fetchTrends(getSystemLanguage())
                newsViewModel.uiState.collect {
                    if (it.message.isNotEmpty()) {
                        context?.showMessage(it.message)
                        return@collect
                    }

                    progressBarLayout.isVisible = !it.isDataLoaded
                    newsAdapter.update(it.articles)
                }
            }
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        progressBarLayout.isVisible = true
        viewLifecycleOwner.lifecycleScope.launch {
            newsViewModel.fetchArticles(checkNotNull(query), getSystemLanguage())
        }
        return false
    }

    override fun onQueryTextChange(newText: String?) = false
}