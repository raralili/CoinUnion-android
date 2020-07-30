package com.rius.coinunion.ui.userlist

import androidx.lifecycle.ViewModel
import com.rius.coinunion.db.SimData
import com.rius.coinunion.entity.user.UserInfo
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class UserListViewModel @Inject constructor() : ViewModel() {

    fun getRecommendUsers(): Flowable<List<UserInfo>> {
        val recommend = SimData().users
        return Flowable.just(recommend).observeOn(AndroidSchedulers.mainThread())
    }
}
