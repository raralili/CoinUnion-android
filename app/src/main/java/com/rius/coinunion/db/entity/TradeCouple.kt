package com.rius.coinunion.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class TradeCouple(
    @PrimaryKey
    val symbol: String,
    val base: String,
    val currency: String
) {

    fun showUppercase(value: String): String {
        return value.toUpperCase(Locale.getDefault())
    }

    fun showUppercaseCoupleBySeparator(separator: String): String {
        val sb = StringBuffer().append(base)
            .append(separator)
            .append(currency)
        return sb.toString().toUpperCase(Locale.getDefault())
    }
}