package com.rius.coinunion.helper

import com.rius.coinunion.db.UserStorage

object LoginInterceptor {

    inline fun intercept(doOnLogin: () -> Unit, donOnLogOut: () -> Unit) {
        if (UserStorage.instance.isLogin()) {
            doOnLogin()
        } else {
            donOnLogOut()
        }
    }
}