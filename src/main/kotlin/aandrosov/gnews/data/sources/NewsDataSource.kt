package aandrosov.gnews.data.sources

import aandrosov.gnews.data.models.Article
import aandrosov.gnews.data.models.Query

interface NewsDataSource {
    fun search(query: Query): List<Article>
    fun trends(query: Query): List<Article>
}