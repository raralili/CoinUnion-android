package com.rius.coinunion.ui.profile

import androidx.lifecycle.ViewModel
import com.rius.coinunion.db.SimData
import com.rius.coinunion.entity.user.UserInfo
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class ProfileViewModel @Inject constructor() : ViewModel() {

    fun userProfile(uid: String): Flowable<UserInfo> {
        val profile = SimData().adminUser
        return Flowable.just(profile).observeOn(AndroidSchedulers.mainThread())
    }

    fun getRecommendUsers(): Flowable<List<UserInfo>> {
        val recommend = SimData().users
        return Flowable.just(recommend).observeOn(AndroidSchedulers.mainThread())
    }

}
