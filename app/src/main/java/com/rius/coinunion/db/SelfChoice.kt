package com.rius.coinunion.db

object SelfChoice {

    fun getIndex(name: String): Int {
        return when (name) {
            "BTC" -> 0
            "ETH" -> 1
            "BCH" -> 2
            "EOS" -> 3
            else -> -1
        }
    }

    fun getCount(): Int {
        return 4
    }
}