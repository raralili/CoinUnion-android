package com.rius.coinunion.utils

import android.content.Context
import android.widget.Toast
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

object KmUtils {

    private lateinit var context: Context

    const val TAG = "kimino"

    fun init(context: Context) {
        this.context = context
    }

    fun initLogger() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .tag(TAG).build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }

    fun toast(str: String) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
    }
}