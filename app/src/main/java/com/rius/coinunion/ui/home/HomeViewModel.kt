package com.rius.coinunion.ui.home

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.orhanobut.logger.Logger
import com.rius.coinunion.entity.spot.CoinInfo
import com.rius.coinunion.entity.spot.socket.BaseInfo
import com.rius.coinunion.entity.spot.socket.KLineInfo
import com.rius.coinunion.helper.CoinInfoConverter
import com.rius.coinunion.injector.NetworkModule
import com.rius.coinunion.utils.ApiUtils
import okhttp3.*
import okio.ByteString
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeViewModel @Inject constructor() : ViewModel() {

    private val webSocketClient = MyWebSocketClient()

    fun connectWebSocket() {
        webSocketClient.connect(NetworkModule.SOCKET_URL)
    }

    fun onSocketData(action: (result: CoinInfo) -> Unit) {
        webSocketClient.onData = action
    }

    fun disconnectWebSocket() {
        webSocketClient.disconnect()
    }

    inner class MyWebSocketClient : WebSocketListener() {

        var onData: ((result: CoinInfo) -> Unit)? = null
        private var webSocket: WebSocket? = null

        fun connect(url: String) {
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build()
            val request = Request.Builder().url(url).build()
            webSocket = okHttpClient.newWebSocket(request, this)
            okHttpClient.dispatcher().executorService().shutdown()
        }

        fun disconnect() {
            webSocket?.close(1000, "page left")
        }

        private val handler = Handler()

        override fun onOpen(webSocket: WebSocket, response: Response) {
            Logger.d("连接成功")
            //subscribe
            //{ "sub": "topic to sub", "id": "id generate by client" }
            //market.$symbol$.kline.$period$
            val gson = Gson()
            val btc = hashMapOf<String, String>()
            btc["sub"] = "market.btcusdt.kline.1day"
            btc["id"] = "rius"
            val btcJson = gson.toJson(btc)
            webSocket.send(btcJson)

            val eth = hashMapOf<String, String>()
            eth["sub"] = "market.ethusdt.kline.1day"
            eth["id"] = "rius"
            val ethJson = gson.toJson(eth)
            webSocket.send(ethJson)

            val bch = hashMapOf<String, String>()
            bch["sub"] = "market.bchusdt.kline.1day"
            bch["id"] = "rius"
            val bchJson = gson.toJson(bch)
            webSocket.send(bchJson)

            val eos = hashMapOf<String, String>()
            eos["sub"] = "market.eosusdt.kline.1day"
            eos["id"] = "rius"
            val eosJson = gson.toJson(eos)
            webSocket.send(eosJson)
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            Logger.d("连接断开")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Logger.d("[socket连接失败]-ERROR:${t.message}-RESPONSE:${response?.message()}")
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
                } else if (obj.has("subbed")) {
                    //todo: do on subbed
                } else {
                    val gson = Gson()
                    val type =
                        SocketParameterizedType(
                            BaseInfo::class.java,
                            arrayOf(KLineInfo::class.java)
                        )
                    val entity = gson.fromJson<BaseInfo<KLineInfo>>(result, type)
                    val kLineInfo = entity.tick
                    val coinInfo = CoinInfo(
                        CoinInfoConverter.convertName(entity.ch),
                        kLineInfo.close,
                        CoinInfoConverter.convertCNY(kLineInfo.close),
                        CoinInfoConverter.getPercent(kLineInfo.open, kLineInfo.close)
                    )
                    onData?.invoke(coinInfo)
                }
            }
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