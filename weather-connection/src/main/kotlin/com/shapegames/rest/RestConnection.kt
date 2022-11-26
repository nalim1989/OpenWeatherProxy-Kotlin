package com.shapegames.rest

import com.shapegames.exceptions.ConnectionException
import okhttp3.OkHttpClient
import okhttp3.Request

class RestConnection {

    private val client:OkHttpClient = OkHttpClient()

    fun syncGet(url:String):String? {
        val request = Request.Builder()
            .get()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw ConnectionException("Unexpected code ${response.code}")

            return  response.body?.string()
        }
    }
}