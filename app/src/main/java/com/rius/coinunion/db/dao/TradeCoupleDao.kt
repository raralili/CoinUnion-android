package com.rius.coinunion.db.dao

import androidx.room.*
import com.rius.coinunion.db.entity.TradeCouple
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface TradeCoupleDao {

    @Query("SELECT * FROM tradecouple")
    fun loadAll(): Flowable<List<TradeCouple>>

    @Query("SELECT * FROM tradecouple WHERE symbol=(:symbol)")
    fun loadBySymbol(symbol: String): Flowable<TradeCouple>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg tradeCouples: TradeCouple): Completable

    @Delete
    fun delete(tradeCouple: TradeCouple): Single<Int>
}