package com.rius.coinunion.entity.user

import com.rius.coinunion.entity.writing.WritingInfo

data class SimpleUserInfo(
    val uid: String,
    val avatar: String,
    val writings: List<WritingInfo>?
)