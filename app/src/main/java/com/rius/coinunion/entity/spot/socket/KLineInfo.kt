package com.rius.coinunion.entity.spot.socket

data class KLineInfo(
    val id: String,
    val open: Float,
    val close: Float,
    val low: Float,
    val high: Float,
    val amount: Float,
    val vol: Float,
    val count: Int
) {
    //{"ch":"market.ethusdt.kline.60min","ts":1594002383416,"tick":{"id":1594000800,"open":228.05,"close":228.27,"low":228.05,"high":228.8,
    // "amount":15312.546837276024,"vol":3498545.1136168577,"count":10358}}
}