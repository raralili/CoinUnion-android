package com.rius.coinunion.ui.home.tradecouple

import com.rius.coinunion.db.entity.TradeCouple
import java.util.*

class TradeCoupleState(
    val tradeCouple: TradeCouple,
    var isChecked: Boolean,
    var isLocal: Boolean
) {
    fun showUppercase(value: String): String {
        return value.toUpperCase(Locale.getDefault())
    }

    fun showUppercaseCoupleBySeparator(separator: String): String {
        val sb = StringBuffer().append(tradeCouple.base)
            .append(separator)
            .append(tradeCouple.currency)
        return sb.toString().toUpperCase(Locale.getDefault())
    }
}