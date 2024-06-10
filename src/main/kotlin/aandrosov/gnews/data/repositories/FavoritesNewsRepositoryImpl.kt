package aandrosov.gnews.data.repositories

import aandrosov.gnews.data.database.daos.LocalArticleDao
import aandrosov.gnews.data.database.entity.LocalArticle
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoritesNewsRepositoryImpl(
    private val localArticleDao: LocalArticleDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : FavoritesNewsRepository {
    override suspend fun fetchFavorites() = withContext(dispatcher) {
        localArticleDao.getAll()
    }

    override suspend fun addToFavorites(url: String, title: String, publishedAt: String, imageUrl: String) {
        return withContext(dispatcher) {
            localArticleDao.insert(
                LocalArticle(
                    id = 0,
                    url = url,
                    title = title,
                    publishedAt = publishedAt,
                    imageUrl = imageUrl
                )
            )
        }
    }

    override suspend fun removeFromFavorites(id: Int) = withContext(dispatcher) {
        localArticleDao.deleteById(id)
    }
}