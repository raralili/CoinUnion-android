package com.rius.coinunion.ui.writing.edit

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.rius.coinunion.db.dao.WritingDao
import com.rius.coinunion.db.entity.WritingInfo
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class WritingEditViewModel @Inject constructor(private val writingDao: WritingDao) : ViewModel() {


    fun publish(title: String, content: String, imgUris: List<Uri>?): Completable {
        val author = "admin01"
        val createTime = Date().time
        val writing =
            WritingInfo(
                "${author}_$createTime",
                author,
                title,
                content,
                imgUris?.map { it.toString() },
                createTime
            )
        return writingDao.insert(writing).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
