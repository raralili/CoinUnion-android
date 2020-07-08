package com.rius.coinunion.helper

import java.util.*
import kotlin.math.roundToInt

object CoinInfoConverter {

    fun convertName(ch: String): String {
        val tradeCouple = ch.split(".")[1]
        if (tradeCouple.contains("usdt")) {
            return tradeCouple.removeSuffix("usdt").toUpperCase(Locale.getDefault())
        }
        return tradeCouple.toUpperCase(Locale.getDefault())
    }

    fun convertCNY(priceUSDT: Float): Float {
        return ((priceUSDT * 7 * 100) / 100).roundToInt().toFloat()
    }

    fun getPercent(open: Float, close: Float): Float {
        return (close - open) / open
    }
}