package aandrosov.gnews.data.repositories

import aandrosov.gnews.data.database.daos.LocalArticleDao
import aandrosov.gnews.data.database.entity.ArticleEntity
import aandrosov.gnews.data.models.Article
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

    override suspend fun addToFavorites(article: Article) {
        return withContext(dispatcher) {
            localArticleDao.insert(
                ArticleEntity(
                    id = 0,
                    url = article.url,
                    title = article.title,
                    content = article.content,
                    description = article.description,
                    publishedAt = article.publishedAt,
                    imageUrl = article.imageUrl,
                    source = article.source
                )
            )
        }
    }

    override suspend fun removeFromFavorites(id: Int) = withContext(dispatcher) {
        localArticleDao.deleteById(id)
    }
}