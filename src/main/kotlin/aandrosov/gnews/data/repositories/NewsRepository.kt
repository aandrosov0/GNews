package aandrosov.gnews.data.repositories

import aandrosov.gnews.data.models.Article
import aandrosov.gnews.data.models.Query

interface NewsRepository {
    suspend fun search(query: Query): List<Article>
    suspend fun trends(query: Query): List<Article>
}