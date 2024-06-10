package aandrosov.gnews.ui.states

data class ArticleUiState(
    val url: String = "",
    val title: String = "",
    val imageUrl: String = "",
    val publishedAt: String = "",
    val isFavorite: Boolean = false,
    val onFavorite: () -> Unit = {}
)
