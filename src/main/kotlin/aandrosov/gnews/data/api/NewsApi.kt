package aandrosov.gnews.data.api

import aandrosov.gnews.data.models.Article

interface NewsApi {
    fun search(query: NewsApiQuery): List<Article>
    fun trends(query: NewsApiQuery): List<Article>
}