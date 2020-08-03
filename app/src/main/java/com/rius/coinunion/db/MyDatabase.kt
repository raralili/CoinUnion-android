package com.rius.coinunion.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rius.coinunion.db.dao.TradeCoupleDao
import com.rius.coinunion.db.entity.TradeCouple

@Database(entities = [TradeCouple::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun tradeCoupleDao(): TradeCoupleDao
}