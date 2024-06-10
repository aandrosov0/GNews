package aandrosov.gnews.ui.states

data class NewsUiState(
    val articles: List<ArticleUiState> = emptyList(),
    val isDataLoaded: Boolean = false,
    val message: String = ""
)
