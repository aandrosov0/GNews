package aandrosov.gnews.data.models

data class Query(
    val page: Int = 1,
    val query: String = "",
    val language: String = "",
)
