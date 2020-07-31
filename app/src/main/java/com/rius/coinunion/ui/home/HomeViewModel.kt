package com.rius.coinunion.ui.home

import com.google.gson.GsonBuilder
import com.rius.coinunion.entity.market.CoinInfo
import com.rius.coinunion.entity.market.socket.BaseInfo
import com.rius.coinunion.entity.market.socket.KLineInfo
import com.rius.coinunion.helper.CoinInfoConverter
import com.rius.coinunion.supercomponent.WebSocketViewModel
import okhttp3.WebSocket
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import javax.inject.Inject

class HomeViewModel @Inject constructor() : WebSocketViewModel() {

    private var onData: ((result: CoinInfo) -> Unit)? = null

    fun onSocketData(action: (result: CoinInfo) -> Unit) {
        this.onData = action
    }

    init {
        addTopic(mapOf(Pair("sub", "market.btcusdt.kline.1day"), Pair("id", "rius")))
        addTopic(mapOf(Pair("sub", "market.ethusdt.kline.1day"), Pair("id", "rius")))
        addTopic(mapOf(Pair("sub", "market.bchusdt.kline.1day"), Pair("id", "rius")))
        addTopic(mapOf(Pair("sub", "market.eosusdt.kline.1day"), Pair("id", "rius")))
    }

    override fun onResult(webSocket: WebSocket, result: String) {
        val gson = GsonBuilder().create()
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