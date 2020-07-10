package com.rius.coinunion.supercomponent

import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.orhanobut.logger.Logger
import com.rius.coinunion.injector.NetworkModule
import com.rius.coinunion.websocket.HuobiWebSocketClient
import com.rius.coinunion.websocket.HuobiWebSocketListener
import okhttp3.Response
import okhttp3.WebSocket
import okio.ByteString

abstract class WebSocketViewModel : ViewModel(), HuobiWebSocketListener {

    protected val webSocketClient = HuobiWebSocketClient()

    fun registerMessageListener() {
        webSocketClient.registerMessageListener(this)
    }

    fun unregisterMessageListener() {
        webSocketClient.unregisterMessageListener(this)
    }

    fun connectWebSocket() {
        webSocketClient.connect(NetworkModule.SOCKET_URL)
    }

    fun addTopic(topic: Map<String, String>) {
        webSocketClient.addTopic(topic)
    }

    fun disconnectWebSocket() {
        webSocketClient.disconnect(1000, "page left")
    }

    override fun onConnected(webSocket: WebSocket, response: Response) {
        Logger.d("[webSocket]: Connected with ${NetworkModule.SOCKET_URL}")
        webSocketClient.subscribe()
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {}

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {}

    override fun onMessage(webSocket: WebSocket, byte: ByteString) {}

    override fun onMessage(webSocket: WebSocket, text: String) {}

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {}

    override fun onSubscribed(webSocket: WebSocket, obj: JsonObject) {}
}