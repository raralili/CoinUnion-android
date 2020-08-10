package com.rius.coinunion.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.rius.coinunion.db.converter.StringListTypeConverter

@Entity(tableName = "writing_info")
@TypeConverters(StringListTypeConverter::class)
data class WritingInfo(
    @PrimaryKey
    val id: String,
    val authorUid: String,
    val title: String,
    val content: String,
    val imgs: List<String>?,
    val createTime: Long
)