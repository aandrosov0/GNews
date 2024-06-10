package aandrosov.gnews.ui.viewModels

import aandrosov.gnews.data.database.entity.LocalArticle
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

    private suspend fun articleUiStateFrom(article: Article): ArticleUiState {
        suspend fun getFavorite(): LocalArticle? {
            return favoritesRepository.fetchFavorites().firstOrNull {
                article.title == it.title
                        && article.imageUrl == it.imageUrl
                        && article.publishedAt == it.publishedAt
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
                    val articleId = getFavorite()?.id

                    articleId ?: return@launch favoritesRepository.addToFavorites(
                        url = article.url,
                        title = article.title,
                        publishedAt = article.publishedAt,
                        imageUrl = article.imageUrl
                    )

                    favoritesRepository.removeFromFavorites(articleId)
                }
            }
        )
    }

    private fun articleUiStateFrom(localArticle: LocalArticle): ArticleUiState {
        return ArticleUiState(
            url = localArticle.url,
            title = localArticle.title,
            imageUrl = localArticle.imageUrl,
            publishedAt = localArticle.publishedAt,
            isFavorite = true,
            onFavorite = {
                viewModelScope.launch {
                    val articleId = favoritesRepository.fetchFavorites().firstOrNull {
                        localArticle == it
                    }?.id

                    articleId ?: return@launch favoritesRepository.addToFavorites(
                        url = localArticle.url,
                        title = localArticle.title,
                        publishedAt = localArticle.publishedAt,
                        imageUrl = localArticle.imageUrl
                    )

                    favoritesRepository.removeFromFavorites(articleId)
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
                newsRepository.search(q).map { articleUiStateFrom(it) }
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
                newsRepository.trends(q).map { articleUiStateFrom(it) }
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
                favoritesRepository.fetchFavorites().map { articleUiStateFrom(it) }
            } catch (exception: Exception) {
                mutableUiState.value = NewsUiState(message = exception.toString())
                return@launch
            }

            mutableUiState.value = NewsUiState(articles, true)
        }
    }
}