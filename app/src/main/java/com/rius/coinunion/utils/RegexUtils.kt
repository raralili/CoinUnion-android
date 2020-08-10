package com.rius.coinunion.utils

import java.util.regex.Pattern


object RegexUtils {

    fun isMobile(str: String): Boolean {
        val pattern = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$")
        val matcher = pattern.matcher(str)
        return matcher.matches()
    }
}