package com.rius.coinunion.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.rius.coinunion.db.entity.WritingInfo
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface WritingDao {

    @Query("SELECT * FROM writing_info ORDER BY createTime DESC")
    fun loadAll(): Flowable<List<WritingInfo>>

    @Query("SELECT * FROM writing_info WHERE id=(:id)")
    fun loadById(id: Long): Flowable<WritingInfo>

    @Insert
    fun insert(vararg info: WritingInfo): Completable

    @Delete
    fun delete(info: WritingInfo): Single<Int>
}