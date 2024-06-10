package aandrosov.gnews.data.api.gnews

import aandrosov.gnews.data.api.NewsApiQuery

data class GNewsTrendsQuery(
    val lang: String,
    val page: Int
) : NewsApiQuery