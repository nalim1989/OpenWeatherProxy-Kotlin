package com.shapegames.rest

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class RestConnection {

    private val client:OkHttpClient = OkHttpClient()

    fun syncGet(url:String):String {
        val request = Request.Builder()
            .get()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            //TODO handle exceptions
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            return  response.body?.let { return it.string() } ?: throw IOException("Malformed response") //TODO handle exception
        }
    }
}