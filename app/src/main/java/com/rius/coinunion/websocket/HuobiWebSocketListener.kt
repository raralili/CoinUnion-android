package com.rius.coinunion.websocket

import com.google.gson.JsonObject
import okhttp3.Response
import okhttp3.WebSocket
import okio.ByteString

interface HuobiWebSocketListener {

    fun onConnected(webSocket: WebSocket, response: Response)

    fun onClosed(webSocket: WebSocket, code: Int, reason: String)

    fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?)

    fun onMessage(webSocket: WebSocket, byte: ByteString)

    fun onMessage(webSocket: WebSocket, text: String)

    fun onClosing(webSocket: WebSocket, code: Int, reason: String)

    fun onSubscribed(webSocket: WebSocket, obj: JsonObject)

    fun onResult(webSocket: WebSocket, result: String)
}