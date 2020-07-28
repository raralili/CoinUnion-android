package com.rius.coinunion.ui.profile

import androidx.lifecycle.ViewModel
import com.rius.coinunion.entity.user.SimpleUserInfo
import com.rius.coinunion.entity.user.UserProfileInfo
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileViewModel @Inject constructor() : ViewModel() {

    fun userProfile(uid: String): Flowable<UserProfileInfo> {
        val profile = UserProfileInfo(
            uid = uid,
            name = "拉尔夫·纳尔逊·艾略特",
            avatar = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595848272663&di=a58f6c59f59b2eb40ae261cce2a7242d&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201410%2F09%2F20141009224754_AswrQ.jpeg",
            stars = listOf(
                SimpleUserInfo(
                    "54088",
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595848433972&di=8f8c08fcf209401264d2c2954e84555d&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201612%2F25%2F20161225202928_mfwSk.thumb.700_0.jpeg",
                    listOf()
                )
            ),
            writings = listOf()
        )
        return Flowable.create<UserProfileInfo>({ emitter ->
            emitter.onNext(profile)
        }, BackpressureStrategy.LATEST)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getUserP(uid: String): UserProfileInfo {
        return UserProfileInfo(
            uid = uid,
            name = "拉尔夫·纳尔逊·艾略特",
            avatar = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595848272663&di=a58f6c59f59b2eb40ae261cce2a7242d&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201410%2F09%2F20141009224754_AswrQ.jpeg",
            stars = listOf(
                SimpleUserInfo(
                    "54088",
                    "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595848433972&di=8f8c08fcf209401264d2c2954e84555d&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201612%2F25%2F20161225202928_mfwSk.thumb.700_0.jpeg",
                    listOf()
                )
            ),
            writings = listOf()
        )
    }

    fun getRecommendUsers(): Flowable<ArrayList<SimpleUserInfo>> {
        val recommend = arrayListOf(
            SimpleUserInfo(
                "88484",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595931428393&di=2469ae16951539f6e6590c68683b8020&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201611%2F08%2F20161108095922_tXR8s.thumb.700_0.jpeg",
                listOf()
            ),
            SimpleUserInfo(
                "7789",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595931428393&di=2469ae16951539f6e6590c68683b8020&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201611%2F08%2F20161108095922_tXR8s.thumb.700_0.jpeg",
                listOf()
            ),
            SimpleUserInfo(
                "84940",
                "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595931428393&di=2469ae16951539f6e6590c68683b8020&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201611%2F08%2F20161108095922_tXR8s.thumb.700_0.jpeg",
                listOf()
            )
        )
        return Flowable.just(recommend).observeOn(AndroidSchedulers.mainThread())
    }

}
