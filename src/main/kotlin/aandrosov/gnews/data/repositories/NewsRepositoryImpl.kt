package aandrosov.gnews.data.repositories

import aandrosov.gnews.data.models.Query
import aandrosov.gnews.data.sources.NewsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NewsRepositoryImpl(
    private val remoteDataSource: NewsDataSource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : NewsRepository {
    override suspend fun search(query: Query) = withContext(dispatcher) {
        remoteDataSource.search(query)
    }

    override suspend fun trends(query: Query) = withContext(dispatcher) {
        remoteDataSource.trends(query)
    }
}