package aandrosov.gnews.data.database

import aandrosov.gnews.data.database.daos.LocalArticleDao
import aandrosov.gnews.data.database.entity.LocalArticle
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [LocalArticle::class], version = 1)
abstract class GNewsDatabase : RoomDatabase() {
    companion object {
        private lateinit var instance: GNewsDatabase

        fun initialize(context: Context) {
            if (::instance.isInitialized) {
                throw IllegalStateException()
            }

            instance = Room.databaseBuilder(
                context,
                GNewsDatabase::class.java,
                "gnews-database"
            ).build()
        }

        fun getInstance(): GNewsDatabase {
            if (!::instance.isInitialized) {
                throw IllegalStateException()
            }
            return instance
        }
    }

    abstract fun articleDao(): LocalArticleDao
}