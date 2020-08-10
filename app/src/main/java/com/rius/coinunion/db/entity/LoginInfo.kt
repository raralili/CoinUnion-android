package com.rius.coinunion.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login_info")
data class LoginInfo(
    @PrimaryKey
    val token: String,
    val periodDay: Int,
    val createDate: Long
)