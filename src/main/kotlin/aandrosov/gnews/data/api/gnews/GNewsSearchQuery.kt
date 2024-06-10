package aandrosov.gnews.data.api.gnews

import aandrosov.gnews.data.api.NewsApiQuery

data class GNewsSearchQuery(
    val q: String,
    val page: Int,
    val lang: String,
) : NewsApiQuery