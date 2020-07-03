package com.rius.coinunion

import android.app.Application
import com.rius.coinunion.websocket.WebSocketClient
import com.rius.coinunion.injector.DaggerAppComponent
import com.rius.coinunion.injector.MyAppInjector
import com.rius.coinunion.utils.KmUtils
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MyApp : Application(), HasAndroidInjector {

    companion object {
        lateinit var instance: MyApp
            private set
    }

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var webSocketClient: WebSocketClient

    override fun androidInjector(): AndroidInjector<Any> = injector

    override fun onCreate() {
        super.onCreate()
        instance = this
        MyAppInjector.init(this) {
            DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)
        }
        KmUtils.initLogger()
    }
}