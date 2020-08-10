package com.rius.coinunion.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rius.coinunion.db.dao.LoginDao
import com.rius.coinunion.db.dao.TradeCoupleDao
import com.rius.coinunion.db.dao.WritingDao
import com.rius.coinunion.db.entity.LoginInfo
import com.rius.coinunion.db.entity.TradeCouple
import com.rius.coinunion.db.entity.WritingInfo

@Database(entities = [TradeCouple::class, LoginInfo::class, WritingInfo::class], version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun tradeCoupleDao(): TradeCoupleDao

    abstract fun loginDao(): LoginDao

    abstract fun writingDao(): WritingDao
}