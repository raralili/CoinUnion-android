package com.rius.coinunion.entity.market.socket

data class BaseInfo<T>(
    val ch: String,
    val ts: String,
    val tick: T
) {
}