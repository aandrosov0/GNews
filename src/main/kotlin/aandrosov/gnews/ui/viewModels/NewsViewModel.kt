package aandrosov.gnews.ui.viewModels

import aandrosov.gnews.data.database.entity.ArticleEntity
import aandrosov.gnews.data.database.entity.asExternalModel
import aandrosov.gnews.data.models.Article
import aandrosov.gnews.data.models.Query
import aandrosov.gnews.data.repositories.FavoritesNewsRepository
import aandrosov.gnews.data.repositories.NewsRepository
import aandrosov.gnews.ui.states.ArticleUiState
import aandrosov.gnews.ui.states.NewsUiState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NewsViewModel(
    private val newsRepository: NewsRepository,
    private val favoritesRepository: FavoritesNewsRepository
) : ViewModel() {
    private val mutableUiState = MutableStateFlow(NewsUiState())
    val uiState = mutableUiState.asStateFlow()

    private var fetchArticlesJob: Job? = null
    private var fetchTrendsJob: Job? = null
    private var fetchFavoritesJob: Job? = null

    private suspend fun convertToArticleUiState(article: Article): ArticleUiState {
        suspend fun getFavorite(): ArticleEntity? {
            return favoritesRepository.fetchFavorites().firstOrNull {
                article == it.asExternalModel()
            }
        }

        return ArticleUiState(
            url = article.url,
            title = article.title,
            imageUrl = article.imageUrl,
            publishedAt = article.publishedAt,
            isFavorite = getFavorite() != null,
            onFavorite = {
                viewModelScope.launch {
                    val favorite = getFavorite()

                    favorite ?: return@launch favoritesRepository.addToFavorites(article)
                    favoritesRepository.removeFromFavorites(favorite.id)
                }
            }
        )
    }

    fun fetchArticles(query: String, language: String, page: Int = 1) {
        fetchArticlesJob?.cancel()
        fetchArticlesJob = viewModelScope.launch {
            val q = Query(
                page = page,
                query = query,
                language = language
            )
            val articles = try {
                newsRepository.search(q).map { convertToArticleUiState(it) }
            } catch (exception: Exception) {
                mutableUiState.value = NewsUiState(message = exception.toString())
                return@launch
            }

            mutableUiState.value = NewsUiState(articles, true)
        }
    }

    fun fetchTrends(language: String, page: Int = 1) {
        fetchTrendsJob?.cancel()
        fetchTrendsJob = viewModelScope.launch {
            val q = Query(
                page = page,
                language = language
            )
            val articles = try {
                newsRepository.trends(q).map { convertToArticleUiState(it) }
            } catch (exception: Exception) {
                mutableUiState.value = NewsUiState(message = exception.toString())
                return@launch
            }

            mutableUiState.value = NewsUiState(articles, true)
        }
    }

    fun fetchFavorites() {
        fetchFavoritesJob?.cancel()
        fetchFavoritesJob = viewModelScope.launch {
            val articles = try {
                favoritesRepository.fetchFavorites().map { convertToArticleUiState(it.asExternalModel()) }
            } catch (exception: Exception) {
                mutableUiState.value = NewsUiState(message = exception.toString())
                return@launch
            }

            mutableUiState.value = NewsUiState(articles, true)
        }
    }
}