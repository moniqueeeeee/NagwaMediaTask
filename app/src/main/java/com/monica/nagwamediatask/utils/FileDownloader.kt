package com.monica.nagwamediatask.utils

import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.net.HttpURLConnection
import javax.inject.Inject

class FileDownloader @Inject constructor(var okHttpClient: OkHttpClient) {

    fun download(url: String, file: File): Observable<Int> {
        return Observable.create { emitter ->
            val request = Request.Builder().url(url).build()
            val response = okHttpClient.newCall(request).execute()
            val body = response.body()
            val responseCode = response.code()
            if (responseCode >= HttpURLConnection.HTTP_OK &&
                responseCode < HttpURLConnection.HTTP_MULT_CHOICE &&
                body != null
            ) {
                val length = body.contentLength()
                body.byteStream().apply {
                    file.outputStream().use { fileOut ->
                        var bytesCopied = 0
                        val buffer = ByteArray(Companion.BUFFER_LENGTH_BYTES)
                        var bytes = read(buffer)
                        while (bytes >= 0) {
                            fileOut.write(buffer, 0, bytes)
                            bytesCopied += bytes
                            bytes = read(buffer)
                            emitter.onNext(((bytesCopied * 100) / length).toInt())
                        }
                    }
                    emitter.onComplete()
                }
            } else {
                throw IllegalArgumentException("Error occurred when do http get $url")
            }
        }
    }

    companion object {
        private const val BUFFER_LENGTH_BYTES = 1024 * 8
    }
}