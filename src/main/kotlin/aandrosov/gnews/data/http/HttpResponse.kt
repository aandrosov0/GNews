package aandrosov.gnews.data.http

data class HttpResponse(
    val code: Int,
    val data: ByteArray,
    val headers: Map<String, String>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HttpResponse

        if (code != other.code) return false
        if (!data.contentEquals(other.data)) return false
        if (headers != other.headers) return false

        return true
    }

    override fun hashCode(): Int {
        var result = code
        result = 31 * result + data.contentHashCode()
        result = 31 * result + headers.hashCode()
        return result
    }
}
