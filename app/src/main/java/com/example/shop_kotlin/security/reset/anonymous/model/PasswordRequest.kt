package com.example.shop_kotlin.security.reset.anonymous.model

data class PasswordRequest (
    var email: String? = null) {

    fun afterEmailChanged(s: CharSequence) {
        email = s.toString()
    }
}