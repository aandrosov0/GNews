package aandrosov.gnews.data.repositories

import aandrosov.gnews.data.database.entity.LocalArticle

interface FavoritesNewsRepository {
    suspend fun fetchFavorites(): List<LocalArticle>
    suspend fun addToFavorites(url: String, title: String, publishedAt: String, imageUrl: String)
    suspend fun removeFromFavorites(id: Int)
}