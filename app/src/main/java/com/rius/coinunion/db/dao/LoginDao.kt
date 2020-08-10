package com.rius.coinunion.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.rius.coinunion.db.entity.LoginInfo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface LoginDao {

    @Query("SELECT * FROM login_info")
    fun load(): Flowable<LoginInfo>

    @Insert
    fun save(info: LoginInfo): Completable

    @Delete
    fun clear(info: LoginInfo): Single<Int>
}