package com.rius.coinunion.ui

import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.leaf.library.StatusBarUtil
import com.rius.coinunion.MainActivity
import com.rius.coinunion.R
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class SplashActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: SplashViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        StatusBarUtil.setTransparentForWindow(this)
        StatusBarUtil.setDarkMode(this)

//        MyApp.instance.webSocketClient.connect(viewModel.getWebSocketHost())

        Handler().postDelayed({
            MainActivity.start(this)
            finish()
        }, 3000)
    }

    override fun androidInjector(): AndroidInjector<Any> = injector
}
