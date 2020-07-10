package com.rius.coinunion.websocket

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.orhanobut.logger.Logger
import com.rius.coinunion.utils.ApiUtils
import okhttp3.Response
import okhttp3.WebSocket
import okio.ByteString
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class HuobiWebSocketClient : WebSocketClient() {

    private val topics = mutableListOf<Map<String, String>>()

    private val listeners = mutableListOf<HuobiWebSocketListener>()

    fun addTopic(topic: Map<String, String>) {
        topics.add(topic)
    }

    fun unsubscribe(topic: Map<String, String>) {
        if (!topics.contains(topic)) {
            Logger.e("topic is not subscribe.")
            return
        }
        TODO("send unsubscribe message.")
    }

    fun subscribe() {
        val gson = GsonBuilder().create()
        if (topics.isEmpty()) {
            Logger.e("no topic is added.please call 'addTopic()' to add the topic you want subscribe.")
            return
        }
        if (webSocket == null) {
            throw NullPointerException("webSocket is not found!")
        }
        topics.forEach { params ->
            val json = gson.toJson(params)
            webSocket!!.send(json)
        }
    }

    fun registerMessageListener(listener: HuobiWebSocketListener) {
        listeners.add(listener)
    }

    fun unregisterMessageListener(listener: HuobiWebSocketListener) {
        listeners.remove(listener)
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        listeners.forEach { l ->
            l.onConnected(webSocket, response)
        }
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        listeners.forEach { l ->
            l.onClosed(webSocket, code, reason)
        }
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        listeners.forEach { l ->
            l.onFailure(webSocket, t, response)
        }
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        val result = String(ApiUtils.decompress(bytes.toByteArray()))
        val gson = GsonBuilder().create()
        val jsonParser = JsonParser()
        val element = jsonParser.parse(result)
        if (element.isJsonObject) {
            val obj = element.asJsonObject
            //keep alive
            if (obj.has("ping")) {
                val ping = obj.get("ping").asString
                val pong = gson.toJson(hashMapOf(Pair("pong", ping)))
                webSocket.send(pong)
            } else if (obj.has("subbed")) {
                listeners.forEach { l ->
                    l.onSubscribed(webSocket, obj)
                }
            } else {
                listeners.forEach { l ->
                    l.onResult(webSocket, result)
                }
            }
        }
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        listeners.forEach { l ->
            l.onMessage(webSocket, text)
        }
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        listeners.forEach { l ->
            l.onClosing(webSocket, code, reason)
        }
    }


    class SocketParameterizedType(
        val raw: Class<*>,
        val args: Array<Type>
    ) : ParameterizedType {


        override fun getRawType(): Type {
            return raw
        }

        override fun getOwnerType(): Type? {
            return null
        }

        override fun getActualTypeArguments(): Array<Type> {
            return args
        }

    }
}