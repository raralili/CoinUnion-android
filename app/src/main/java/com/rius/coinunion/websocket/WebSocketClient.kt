package com.rius.coinunion.websocket

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.orhanobut.logger.Logger
import com.rius.coinunion.utils.ApiUtils
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit

class WebSocketClient : WebSocketListener() {


    private val okHttpClient = OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .build()

    private lateinit var webSocket: WebSocket

    fun connect(url: String) {
        val request = Request.Builder().url(url).build()
        webSocket = okHttpClient.newWebSocket(request, this)
        okHttpClient.dispatcher().executorService().shutdown()
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Logger.d("连接成功")
        //{ "sub": "topic to sub", "id": "id generate by client" }
        //subscribe
        val params = hashMapOf<String, String>()
        params["sub"] = "market.ethusdt.trade.detail"
        params["id"] = "rius"
        val result = Gson().toJson(params)
        webSocket.send(result)
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        Logger.d("连接断开")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Logger.d("连接失败")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        Logger.d("[socket-message]:${text}")
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        val result = String(ApiUtils.decompress(bytes.toByteArray()))
        Logger.d("[socket-message]:${result}")
        val jsonParser = JsonParser()
        val element = jsonParser.parse(result)

        if (element.isJsonObject) {
            val obj = element.asJsonObject
            //keep alive
            if (obj.has("ping")) {
                val ping = obj.get("ping").asString
                val pong = Gson().toJson(hashMapOf(Pair("pong", ping)))
                webSocket.send(pong)
            }
        }
    }
}