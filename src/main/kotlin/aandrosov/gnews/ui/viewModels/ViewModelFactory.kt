package aandrosov.gnews.ui.viewModels

import aandrosov.gnews.data.api.gnews.GNewsApi
import aandrosov.gnews.data.repositories.FavoritesNewsRepositoryImpl
import aandrosov.gnews.data.repositories.NewsRepositoryImpl
import aandrosov.gnews.data.sources.remote.NewsDataSourceRemote
import aandrosov.gnews.data.database.GNewsDatabase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

object ViewModelFactory : ViewModelProvider.Factory {
    private const val GNEWS_API_KEY = ""
    private val newsRepository = NewsRepositoryImpl(NewsDataSourceRemote(GNewsApi(GNEWS_API_KEY)))

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = when (modelClass) {
        NewsViewModel::class.java -> NewsViewModel(
            newsRepository = newsRepository,
            favoritesRepository = FavoritesNewsRepositoryImpl(
                GNewsDatabase.getInstance().articleDao()
            )
        ) as T
        else -> throw IllegalArgumentException(
            "View model class $modelClass doesn't exists"
        )
    }
}