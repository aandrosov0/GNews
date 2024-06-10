package aandrosov.gnews.data.api.gnews

import aandrosov.gnews.data.api.NewsApi
import aandrosov.gnews.data.api.NewsApiQuery
import aandrosov.gnews.data.http.HttpClient
import aandrosov.gnews.data.http.HttpClientImpl
import aandrosov.gnews.data.http.HttpMethod
import aandrosov.gnews.data.http.HttpResponse
import aandrosov.gnews.data.models.Article
import aandrosov.gnews.data.models.Source
import org.json.JSONObject
import java.net.URLEncoder

class GNewsApi(
    private val apiKey: String,
    private val http: HttpClient = HttpClientImpl()
) : NewsApi {
    companion object {
        const val API_URL = "https://gnews.io/api/v4"
    }

    private fun checkResponseExceptions(response: HttpResponse) {
        if (response.code >= 400) {
            throw GNewsApiException("""
                Response Error: ${response.data.decodeToString()}
                Status Code: ${response.code}
            """.trimIndent())
        }
    }

    private fun encodeArticles(json: String): List<Article> {
        val articlesJSONObject = JSONObject(json).getJSONArray("articles")
        return List(articlesJSONObject.length()) {
            val articleJSONObject = articlesJSONObject.getJSONObject(it)
            val sourceJSONObject = articleJSONObject.getJSONObject("source")

            Article(
                title = articleJSONObject.getString("title"),
                description = articleJSONObject.getString("description"),
                content = articleJSONObject.getString("content"),
                url = articleJSONObject.getString("url"),
                imageUrl = articleJSONObject.getString("image"),
                publishedAt = articleJSONObject.getString("publishedAt"),
                source = Source(
                    name = sourceJSONObject.getString("name"),
                    url = sourceJSONObject.getString("url")
                )
            )
        }
    }

    private fun execute(url: String): List<Article> {
        val response = http.request(url, HttpMethod.GET)
        checkResponseExceptions(response)

        return encodeArticles(response.data.decodeToString())
    }

    override fun search(query: NewsApiQuery): List<Article> {
        if (query !is GNewsSearchQuery) {
            throw IllegalArgumentException("""
                Expected "GNewsSearchQuery" parameter type.
                Received "${query.javaClass}".
            """.trimIndent())
        }
        val q = URLEncoder.encode(query.q, "UTF-8")
        return execute(url = "$API_URL/search?apikey=$apiKey&q=\"${q}\"&lang=${query.lang}&page=${query.page}")
    }

    override fun trends(query: NewsApiQuery): List<Article> {
        if (query !is GNewsTrendsQuery) {
            throw IllegalArgumentException("""
                Expected "GNewsTrendsQuery" parameter type.
                Received "${query.javaClass}".
            """.trimIndent())
        }
        return execute(url = "$API_URL/top-headlines?apikey=$apiKey&lang=${query.lang}&page=${query.page}")
    }
}