package aandrosov.gnews.data.database.entity

import aandrosov.gnews.data.models.Article
import aandrosov.gnews.data.models.Source
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val title: String,
    @ColumnInfo val description: String,
    @ColumnInfo val content: String,
    @ColumnInfo val url: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    @ColumnInfo val publishedAt: String,
    @Embedded(prefix = "source") val source: Source
)

fun ArticleEntity.asExternalModel() = Article(
    title = title,
    description = description,
    content = content,
    url = url,
    imageUrl = imageUrl,
    publishedAt = publishedAt,
    source = source
)
