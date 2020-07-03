package com.rius.coinunion.ui.home

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.orhanobut.logger.Logger
import com.rius.coinunion.entity.spot.CoinInfo
import com.rius.coinunion.injector.NetworkModule
import com.rius.coinunion.utils.ApiUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.*
import okio.ByteString
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomeViewModel @Inject constructor() : ViewModel() {

    private val webSocketClient = MyWebSocketClient()

    fun connectWebSocket() {
        webSocketClient.connect(NetworkModule.SOCKET_URL)
    }

    fun onSocketData(action: (result: String) -> Unit) {
        webSocketClient.onData = action
    }

    inner class MyWebSocketClient : WebSocketListener() {

        var onData: ((result: String) -> Unit)? = null

        private val okHttpClient = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()

        fun connect(url: String) {
            val request = Request.Builder().url(url).build()
            okHttpClient.newWebSocket(request, this)
            okHttpClient.dispatcher().executorService().shutdown()
        }

        private val handler = Handler()

        override fun onOpen(webSocket: WebSocket, response: Response) {
            Logger.d("连接成功")
            //{ "sub": "topic to sub", "id": "id generate by client" }
            //subscribe
            handler.postDelayed({
                val params = hashMapOf<String, String>()
                params["sub"] = "market.ethusdt.trade.detail"
                params["id"] = "rius"
                val result = Gson().toJson(params)
                webSocket.send(result)
            }, 5000)
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
                } else {
                    Observable.create<String> { emitter ->
                        emitter.onNext(result)
                    }.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { result ->
                            onData?.invoke(result)
                        }
                }
            }
        }
    }


    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    val text: LiveData<String> = _text

    fun getData(): MutableList<CoinInfo> {
        val list = mutableListOf<CoinInfo>(
            CoinInfo("BTC", 7200f, 53000f, 0.015f),
            CoinInfo("ETH", 230f, 1450f, 0.035f),
            CoinInfo("BCH", 7200f, 53000f, 0.025f),
            CoinInfo("BSV", 7200f, 53000f, 0.055f),
            CoinInfo("EOS", 7200f, 53000f, 0.0015f)
        )
        return list
    }

}