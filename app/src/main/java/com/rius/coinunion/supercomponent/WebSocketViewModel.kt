package com.rius.coinunion.supercomponent

import androidx.lifecycle.ViewModel
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

abstract class WebSocketViewModel : ViewModel() {

    protected fun generateHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .readTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .connectTimeout(10, TimeUnit.SECONDS)
        .build()
}