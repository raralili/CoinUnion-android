package com.rius.coinunion.api.spot

import com.rius.coinunion.api.ApiResult
import com.rius.coinunion.entity.spot.socket.KLineInfo
import com.rius.coinunion.injector.NetworkModule
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface CommonApi {

    @GET("${NetworkModule.MODEL_MARKET}/history/kline")
    fun getKLineInfo(@Query("symbol") symbol: String, @Query("period") period: String, @Query("size") size: Int): Flowable<ApiResult<List<KLineInfo>>>
}