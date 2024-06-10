package aandrosov.gnews.data.database.daos

import aandrosov.gnews.data.database.entity.LocalArticle
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface LocalArticleDao {
    @Query("SELECT * FROM local_article")
    fun getAll(): List<LocalArticle>

    @Insert
    fun insert(localArticle: LocalArticle)

    @Query("DELETE FROM local_article WHERE id = :id")
    fun deleteById(id: Int)
}