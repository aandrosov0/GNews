package aandrosov.gnews.data.repositories

import aandrosov.gnews.data.database.entity.ArticleEntity
import aandrosov.gnews.data.models.Article

interface FavoritesNewsRepository {
    suspend fun fetchFavorites(): List<ArticleEntity>
    suspend fun addToFavorites(article: Article)
    suspend fun removeFromFavorites(id: Int)
}