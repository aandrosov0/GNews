package aandrosov.gnews.data.http

interface HttpClient {
    fun request(
        url: String,
        method: HttpMethod,
        data: ByteArray = ByteArray(0),
        headers: Map<String, String> = emptyMap()
    ): HttpResponse
}