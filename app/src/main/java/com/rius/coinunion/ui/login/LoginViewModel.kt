package com.rius.coinunion.ui.login

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import com.rius.coinunion.R
import com.rius.coinunion.utils.KmUtils
import com.rius.coinunion.utils.RegexUtils
import javax.inject.Inject

class LoginViewModel @Inject constructor() : ViewModel() {

    fun signIn(phone: String, password: String) {
        //验证手机号码
//        if (TextUtils.isEmpty(phone)) {
//            KmUtils.toast(R.string.login_fragment_toast_empty_phone)
//            return@setOnClickListener
//        } else {
//            if (!RegexUtils.isMobile(password)) {
//                KmUtils.toast(resources.getString(R.string.login_fragment_toast_incorrect_phone))
//                return@setOnClickListener
//            }
//        }
    }
}
