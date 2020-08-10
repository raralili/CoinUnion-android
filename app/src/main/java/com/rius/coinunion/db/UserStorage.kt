package com.rius.coinunion.db

class UserStorage private constructor() {

    companion object {
        val instance: UserStorage by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            UserStorage()
        }
    }

    fun isLogin(): Boolean {
        return true
    }
}