package com.rius.coinunion.ui.home.tradecouple

import androidx.lifecycle.ViewModel
import com.rius.coinunion.api.ApiResult
import com.rius.coinunion.api.SpotApi
import com.rius.coinunion.entity.market.TradeCouple
import com.rius.coinunion.entity.vo.JsonTradeCouple
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*
import javax.inject.Inject

class TradeCoupleViewModel @Inject constructor(private val spotApi: SpotApi) : ViewModel() {

    fun getTradeCouples(): Flowable<List<TradeCouple>> {
        return spotApi.getTradeCouples().observeOn(AndroidSchedulers.mainThread())
            .map { result: ApiResult<List<JsonTradeCouple>> ->
                val tradeCoupleList = result.data
                    .filter { it.quoteCurrency == "usdt" }
                    .map {
                        TradeCouple(
                            coupleForView = "${it.baseCurrency}|${it.quoteCurrency}"
                                .toUpperCase(Locale.getDefault()),
                            coupleForRequest = it.symbol
                        )
                    }
                tradeCoupleList
            }
    }
}
