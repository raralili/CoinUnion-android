package com.rius.coinunion.injector

import com.rius.coinunion.websocket.WebSocketClient
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        const val HTTP_URL = "https://api.huobi.pro/"

        const val SOCKET_URL = "wss://api.huobi.pro/ws"

        //公共行情类，包括成交 深度 行情等
        const val MODEL_MARKET = "market"

        const val MODEL_COMMON = "v1/common"
        const val MODEL_ACCOUNT = "v1/account"
        const val MODEL_ORDER = "v1/order"
        const val MODEL_MARGIN = "v1/margin"
        const val MODEL_CROSS_MARGIN = "v1/cross-margin"
    }


    @Provides
    @Singleton
    fun provideWebSocketClient() = WebSocketClient()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
//            .addInterceptor(HttpLoggingInterceptor())
            .connectTimeout(10, TimeUnit.SECONDS)
            .build()
        return client
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(HTTP_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }
}