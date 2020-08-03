package com.rius.coinunion.db

import com.rius.coinunion.db.entity.TradeCouple

object LocalDefaultTradeCouples {

    fun get(): Array<TradeCouple> {
        val btc = TradeCouple("btcusdt", "btc", "usdt")
        val eth = TradeCouple("ethusdt", "eth", "usdt")
        val bch = TradeCouple("bchusdt", "bch", "usdt")
        val eos = TradeCouple("eosusdt", "eos", "usdt")
        return arrayOf(btc, eth, bch, eos)
    }
}