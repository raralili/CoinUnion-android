package com.rius.coinunion.entity.spot.socket

data class BaseInfo<T>(
    val ch: String,
    val ts: String,
    val tick: T
) {
}