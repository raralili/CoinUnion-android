package com.rius.coinunion

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.leaf.library.StatusBarUtil
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = injector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        StatusBarUtil.setTransparentForWindow(this)
        StatusBarUtil.setDarkMode(this)
        StatusBarUtil.setPaddingTop(this, fragment_container)
    }

}
