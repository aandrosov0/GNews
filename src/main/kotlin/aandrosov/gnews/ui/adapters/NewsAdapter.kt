package aandrosov.gnews.ui.adapters

import aandrosov.gnews.ui.states.ArticleUiState
import aandrosov.gnews.ui.views.ArticleCard
import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import coil.request.ImageRequest

class NewsAdapter(
    private var onReadArticleListener: (articleUiState: ArticleUiState) -> Unit = {},
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    class ViewHolder(val view: ArticleCard) : RecyclerView.ViewHolder(view)

    private var items = emptyList<ArticleUiState>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ArticleCard(parent.context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val articleUiState = items[position]
        holder.view.title = articleUiState.title
        holder.view.isFavorite = articleUiState.isFavorite
        holder.view.publishedAt = articleUiState.publishedAt
        holder.view.setOnFavoriteClickListener(articleUiState.onFavorite)
        holder.view.setOnReadClickListener { onReadArticleListener(articleUiState) }

        holder.view.image = null
        val imageRequest = ImageRequest.Builder(holder.view.context)
            .data(articleUiState.imageUrl)
            .target { holder.view.image = it }
            .build()
        holder.view.context.imageLoader.enqueue(imageRequest)
    }

    override fun getItemCount() = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun update(articleUiStates: List<ArticleUiState>) {
        items = articleUiStates
        notifyDataSetChanged()
    }
}