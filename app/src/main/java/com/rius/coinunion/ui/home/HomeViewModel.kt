package com.rius.coinunion.ui.home

import com.google.gson.GsonBuilder
import com.rius.coinunion.db.dao.TradeCoupleDao
import com.rius.coinunion.db.entity.TradeCouple
import com.rius.coinunion.entity.market.CoinInfo
import com.rius.coinunion.entity.market.socket.BaseInfo
import com.rius.coinunion.entity.market.socket.KLineInfo
import com.rius.coinunion.helper.CoinInfoConverter
import com.rius.coinunion.supercomponent.WebSocketViewModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Response
import okhttp3.WebSocket
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val tradeCoupleDao: TradeCoupleDao) :
    WebSocketViewModel() {

    private var onData: ((result: CoinInfo) -> Unit)? = null

    private var onConnected: ((WebSocket, Response) -> Unit)? = null

    fun onSocketData(action: (result: CoinInfo) -> Unit) {
        this.onData = action
    }

    fun onConnectedWebSocket(action: (WebSocket, Response) -> Unit) {
        this.onConnected = action
    }

//    init {
//        addTopic(mapOf(Pair("sub", "market.btcusdt.kline.1day"), Pair("id", "rius")))
//        addTopic(mapOf(Pair("sub", "market.ethusdt.kline.1day"), Pair("id", "rius")))
//        addTopic(mapOf(Pair("sub", "market.bchusdt.kline.1day"), Pair("id", "rius")))
//        addTopic(mapOf(Pair("sub", "market.eosusdt.kline.1day"), Pair("id", "rius")))
//    }

    override fun onConnected(webSocket: WebSocket, response: Response) {
        super.onConnected(webSocket, response)
        onConnected?.invoke(webSocket, response)
    }

    fun loadLocalTradeCouples(): Flowable<List<TradeCouple>> {
        return tradeCoupleDao.loadAll().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun insertTradeCouples(vararg couple: TradeCouple): Completable {
        return tradeCoupleDao.insert(*couple).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
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