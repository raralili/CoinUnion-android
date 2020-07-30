package com.rius.coinunion.entity.user

import com.rius.coinunion.entity.writing.WritingInfo

data class UserProfileInfo(
    val uid: String,
    val name: String,
    val avatar: String,
    val stars: List<UserInfo>?,
    val writings: List<WritingInfo>?
)