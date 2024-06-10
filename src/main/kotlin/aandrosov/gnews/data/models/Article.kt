package aandrosov.gnews.data.models

data class Article(
    val title: String,
    val description: String,
    val content: String,
    val url: String,
    val imageUrl: String,
    val publishedAt: String,
    val source: Source,
)
