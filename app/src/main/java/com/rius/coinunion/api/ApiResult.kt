package com.rius.coinunion.api

data class ApiResult<T>(
    val status: String,
    val ch: String,
    val ts: Long,
    val data: T
)