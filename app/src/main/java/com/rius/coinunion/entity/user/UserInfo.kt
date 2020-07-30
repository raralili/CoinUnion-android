package com.rius.coinunion.entity.user

import com.rius.coinunion.entity.writing.WritingInfo

data class UserInfo(
    val name: String,
    val sign: String,
    val avatar: String,
    val writings: List<WritingInfo>?,
    val fans: List<UserInfo>?,
    val stars: List<UserInfo>?,
    val privateInfo: Private
) {
    data class Private(
        val uid: String
    )
}