package com.rius.coinunion.entity.spot

data class CoinInfo(
    val name: String,
    var priceUSDT: Float,
    var priceCNY: Float,
    var percent: Float
)