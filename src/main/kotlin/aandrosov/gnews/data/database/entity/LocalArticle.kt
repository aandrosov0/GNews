package aandrosov.gnews.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "local_article")
data class LocalArticle(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val url: String,
    @ColumnInfo val title: String,
    @ColumnInfo val publishedAt: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
)
