package com.example.shop_kotlin.login.model

data class LoginRequest(
    var login: String? = null,
    var password: String? = null

) {

    fun afterLoginChanged(s: CharSequence) {
        login = s.toString()
    }

    fun afterPasswordChange(s: CharSequence) {
        password = s.toString()
    }
}