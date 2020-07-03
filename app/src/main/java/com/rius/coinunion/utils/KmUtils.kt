package com.rius.coinunion.utils

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

object KmUtils {

    fun initLogger() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .tag("kimino").build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
    }
}