package com.rius.coinunion.entity.market

data class CoinInfo(
    val name: String,
    var priceUSDT: Float,
    var priceCNY: Float,
    var percent: Float
)