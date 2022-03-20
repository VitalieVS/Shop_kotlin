package com.example.shopkotlin.security.reset.logged.model

class PasswordLoggedResetRequest {
    var currentPassword: String? = null
    var firstChangeToPassword: String? = null
    var secondChangeToPassword: String? = null
    fun afterFirstChangeToPasswordChanged(s: CharSequence) {
        firstChangeToPassword = s.toString()
    }

    fun afterSecondChangeToPasswordChanged(s: CharSequence) {
        secondChangeToPassword = s.toString()
    }

    fun afterCurrentPasswordChanged(s: CharSequence) {
        currentPassword = s.toString()
    }
}