package com.rius.coinunion.entity.spot

data class CandleStickInfo(
    val id: Long,
    val amount: Float,
    val count: Int,
    val open: Float,
    val close: Float,
    val low: Float,
    val high: Float,
    val vol: Float
)