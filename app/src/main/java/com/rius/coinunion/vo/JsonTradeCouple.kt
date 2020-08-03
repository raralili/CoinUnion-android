package com.rius.coinunion.vo

import com.google.gson.annotations.SerializedName

data class JsonTradeCouple(
    @SerializedName("base-currency")
    val baseCurrency: String, //基础币种
    @SerializedName("quote-currency")
    val quoteCurrency: String,//报价币种：如usdt
    @SerializedName("price-precision")
    val pricePrecision: Int, //交易对报价的精度（小数点后位数）
    @SerializedName("amount-precision")
    val amountPrecision: Int,
    @SerializedName("symbol-partition")
    val symbolPartition: String,
    val symbol: String,
    val state: String,
    @SerializedName("value-precision")
    val valuePrecision: Int,
    @SerializedName("min-order-amt")
    val minOrderAmt: Float,
    @SerializedName("max-order-amt")
    val maxOrderAmt: Float,
    @SerializedName("limit-order-min-order-amt")
    val limitOrderMinOrderAmt: Float,
    @SerializedName("limit-order-max-order-amt")
    val limitOrderMaxOrderAmt: Float,
    @SerializedName("sell-market-min-order-amt")
    val sellMarketMinOrderAmt: Float,
    @SerializedName("sell-market-max-order-amt")
    val sellMarketMaxOrderAmt: Float,
    @SerializedName("buy-market-max-order-amt")
    val buyMarketMaxOrderValue: Float,
    @SerializedName("min-order-value")
    val minOrderValue: Float,
    @SerializedName("max-order-value")
    val maxOrderValue: Float,
    @SerializedName("leverage-ratio")
    val leverageRatio: Float
)