package com.rius.coinunion.entity.spot

data class CoinInfo(
    val name: String,
    val priceUSDT: Float,
    val priceCNY: Float,
    val percent: Float
) {
    companion object {
        const val SIGN_CNY = "￥"
    }
}