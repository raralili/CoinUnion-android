package com.rius.coinunion.ui.home.tradecouple

import androidx.lifecycle.ViewModel
import com.rius.coinunion.api.ApiResult
import com.rius.coinunion.api.SpotApi
import com.rius.coinunion.db.dao.TradeCoupleDao
import com.rius.coinunion.db.entity.TradeCouple
import com.rius.coinunion.vo.JsonTradeCouple
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TradeCoupleViewModel @Inject constructor(
    private val spotApi: SpotApi,
    private val tradeCoupleDao: TradeCoupleDao
) : ViewModel() {

    fun loadLocalTradeCouples(): Flowable<List<TradeCouple>> {
        return tradeCoupleDao.loadAll().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun deleteLocalTrade(tradeCouple: TradeCouple): Single<Int> {
        return tradeCoupleDao.delete(tradeCouple).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun addCouple(vararg couple: TradeCouple): Completable {
        return tradeCoupleDao.insert(*couple).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getTradeCouples(): Flowable<List<TradeCouple>> {
        return spotApi.getTradeCouples().observeOn(AndroidSchedulers.mainThread())
            .map { result: ApiResult<List<JsonTradeCouple>> ->
                val tradeCoupleList = result.data
                    .filter { it.quoteCurrency == "usdt" }
                    .map {
                        TradeCouple(
                            symbol = it.symbol,
                            base = it.baseCurrency,
                            currency = it.quoteCurrency
                        )
                    }
                tradeCoupleList
            }
    }

}
