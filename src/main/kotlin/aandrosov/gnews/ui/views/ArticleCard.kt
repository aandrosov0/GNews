package aandrosov.gnews.ui.views

import aandrosov.gnews.R
import aandrosov.gnews.ui.utils.dpToPx
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class ArticleCard(context: Context) : MaterialCardView(context) {
    init {
        LayoutInflater.from(context).inflate(R.layout.article_card, this)
        val customLayoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT)
        customLayoutParams.setMargins(0, 0, 0, context.dpToPx(14f).toInt())
        layoutParams = customLayoutParams
    }

    private val titleTextView: TextView = requireViewById(R.id.title)
    private val imageView: ImageView = requireViewById(R.id.image_view)
    private val readButton: Button = requireViewById(R.id.read_button)
    private val favoriteButton: MaterialButton = requireViewById(R.id.favorite_button)
    private val publishedAtTextView: TextView = requireViewById(R.id.published_at_text_view)

    var title: CharSequence
        get() = titleTextView.text
        set(value) { titleTextView.text = value }

    var publishedAt: CharSequence
        get() = publishedAtTextView.text
        set(value) {
            publishedAtTextView.text = value.split("T", limit = 2)[0]
        }

    var image: Drawable?
        get() = imageView.drawable
        set(value) = imageView.setImageDrawable(value)

    var isFavorite: Boolean = false
        set(value) {
            field = value
            when(value) {
                true -> favoriteButton.setIconResource(R.drawable.favorite)
                false -> favoriteButton.setIconResource(R.drawable.favorite_outline)
            }
        }

    fun setOnReadClickListener(listener: () -> Unit) {
        readButton.setOnClickListener { listener() }
    }

    fun setOnFavoriteClickListener(listener: () -> Unit) {
        favoriteButton.setOnClickListener {
            isFavorite = !isFavorite
            listener()
        }
    }
}