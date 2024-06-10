package aandrosov.gnews.data.sources.remote

import aandrosov.gnews.data.api.NewsApi
import aandrosov.gnews.data.api.gnews.GNewsApi
import aandrosov.gnews.data.api.gnews.GNewsSearchQuery
import aandrosov.gnews.data.api.gnews.GNewsTrendsQuery
import aandrosov.gnews.data.models.Article
import aandrosov.gnews.data.models.Query
import aandrosov.gnews.data.sources.NewsDataSource
import java.lang.IllegalStateException

class NewsDataSourceRemote(
    private val api: NewsApi,
) : NewsDataSource {
    override fun search(query: Query): List<Article> {
        val searchQuery = when {
            api is GNewsApi -> GNewsSearchQuery(
                q = query.query,
                lang = query.language,
                page = query.page
            )
            else -> throw IllegalStateException()
        }
        return api.search(searchQuery)
    }

    override fun trends(query: Query): List<Article> {
        val trendsQuery = when {
            api is GNewsApi -> GNewsTrendsQuery(
                lang = query.language,
                page = query.page
            )
            else -> throw IllegalStateException()
        }
        return api.trends(trendsQuery)
    }
}