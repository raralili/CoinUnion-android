package com.rius.coinunion.websocket

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.concurrent.TimeUnit

abstract class WebSocketClient : WebSocketListener() {


    protected var webSocket: WebSocket? = null

    private fun newHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
        return okHttpClient
    }

    fun connect(url: String) {
        val client = newHttpClient()
        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, this)
        client.dispatcher().executorService().shutdown()
    }

    fun disconnect(code: Int, reason: String?) {
        webSocket?.close(code, reason)
    }
}