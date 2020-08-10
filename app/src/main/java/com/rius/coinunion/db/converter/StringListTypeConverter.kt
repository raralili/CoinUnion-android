package com.rius.coinunion.db.converter

import androidx.room.TypeConverter


class StringListTypeConverter {

    @TypeConverter
    fun fromStringList(value: String): List<String>? {
        return value.split(",")
    }

    @TypeConverter
    fun storeToString(list: List<String>): String {
        val str = StringBuilder(list[0])
        list.forEach {
            str.append(",").append(it)
        }
        return str.toString()
    }
}