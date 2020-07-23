package com.rius.coinunion.ui

import androidx.lifecycle.ViewModel
import com.rius.coinunion.injector.NetworkModule
import javax.inject.Inject

class SplashViewModel @Inject constructor() : ViewModel() {

    
    fun getWebSocketHost(): String {
        return NetworkModule.SOCKET_URL
    }
}