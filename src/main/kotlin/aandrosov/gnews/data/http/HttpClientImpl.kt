package aandrosov.gnews.data.http

import java.net.HttpURLConnection
import java.net.URL

class HttpClientImpl : HttpClient {
    override fun request(
        url: String,
        method: HttpMethod,
        data: ByteArray,
        headers: Map<String, String>
    ): HttpResponse {
        val connection = URL(url).openConnection() as HttpURLConnection
        headers.forEach(connection::setRequestProperty)

        connection.requestMethod = method.name
        if (data.isNotEmpty()) {
            connection.doOutput = true
            connection.outputStream.write(data)
        }

        val responseCode = connection.responseCode
        val responseData = if (responseCode >= 400) {
            connection.errorStream.readBytes()
        } else {
            connection.inputStream.readBytes()
        }

        val responseHeaders = mutableMapOf<String, String>()
        connection.headerFields.forEach {
            val field = StringBuilder()
            it.value.forEach { string ->
                field.append(string).append(" ")
            }
            responseHeaders[it.key] = field.toString()
        }

        connection.disconnect()
        return HttpResponse(responseCode, responseData, responseHeaders)
    }
}