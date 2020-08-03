package com.rius.coinunion.api

import com.rius.coinunion.entity.market.socket.KLineInfo
import com.rius.coinunion.vo.JsonTradeCouple
import com.rius.coinunion.injector.NetworkModule
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface SpotApi {

    @GET("${NetworkModule.MODEL_MARKET}/history/kline")
    fun getKLineInfo(@Query("symbol") symbol: String, @Query("period") period: String, @Query("size") size: Int): Flowable<ApiResult<List<KLineInfo>>>

    @GET("${NetworkModule.MODEL_COMMON}/symbols")
    fun getTradeCouples(): Flowable<ApiResult<List<JsonTradeCouple>>>
}