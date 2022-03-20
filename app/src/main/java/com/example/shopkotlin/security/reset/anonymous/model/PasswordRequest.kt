package com.example.shopkotlin.security.reset.anonymous.model

data class PasswordRequest (
    var email: String? = null) {

    fun afterEmailChanged(s: CharSequence) {
        email = s.toString()
    }
}