package aandrosov.gnews.data.database.daos

import aandrosov.gnews.data.database.entity.ArticleEntity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LocalArticleDao {
    @Query("SELECT * FROM article")
    fun getAll(): List<ArticleEntity>

    @Insert
    fun insert(articleEntity: ArticleEntity)

    @Query("DELETE FROM article WHERE id = :id")
    fun deleteById(id: Int)
}