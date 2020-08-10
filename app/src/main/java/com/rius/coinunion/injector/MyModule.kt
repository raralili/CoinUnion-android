package com.rius.coinunion.injector

import android.app.Application
import androidx.room.Room
import com.rius.coinunion.db.MyDatabase
import com.rius.coinunion.db.dao.LoginDao
import com.rius.coinunion.db.dao.TradeCoupleDao
import com.rius.coinunion.db.dao.WritingDao
import com.rius.coinunion.injector.module.ApiModule
import com.rius.coinunion.injector.module.VMModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [VMModule::class, ApiModule::class])
class MyModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): MyDatabase {
        return Room.databaseBuilder(app, MyDatabase::class.java, "coinunion.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideTradeCoupleDao(database: MyDatabase): TradeCoupleDao {
        return database.tradeCoupleDao()
    }

    @Provides
    @Singleton
    fun provideLoginDao(database: MyDatabase): LoginDao {
        return database.loginDao()
    }

    @Provides
    @Singleton
    fun provideWritingDao(database: MyDatabase): WritingDao {
        return database.writingDao()
    }
}