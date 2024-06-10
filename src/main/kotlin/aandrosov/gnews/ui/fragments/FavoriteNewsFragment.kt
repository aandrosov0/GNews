package aandrosov.gnews.ui.fragments

import aandrosov.gnews.R
import aandrosov.gnews.ui.adapters.NewsAdapter
import aandrosov.gnews.ui.utils.openWebPage
import aandrosov.gnews.ui.utils.showMessage
import aandrosov.gnews.ui.viewModels.NewsViewModel
import aandrosov.gnews.ui.viewModels.ViewModelFactory
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class FavoriteNewsFragment : Fragment(R.layout.favorite_news_fragment) {
    private val newsViewModel: NewsViewModel by viewModels { ViewModelFactory }
    private val newsAdapter = NewsAdapter { requireContext().openWebPage(it.url) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val favoritesRecyclerView = view.requireViewById<RecyclerView>(R.id.news_recycler_view)
        favoritesRecyclerView.adapter = newsAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                newsViewModel.fetchFavorites()
                newsViewModel.uiState.collect {
                    if (it.message.isNotEmpty()) {
                        context?.showMessage(it.message)
                        return@collect
                    }

                    newsAdapter.update(it.articles)
                }
            }
        }
    }
}