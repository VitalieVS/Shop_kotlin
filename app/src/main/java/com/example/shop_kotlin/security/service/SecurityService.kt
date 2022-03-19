package com.example.shop_kotlin.security.service

import com.example.shop_kotlin.security.reset.logged.model.PasswordLoggedResetRequest
import com.example.shop_kotlin.security.reset.logged.viewmodel.ResetPasswordViewModel

class SecurityService {
    private var token: String? = null
    private var resetPasswordViewModel: ResetPasswordViewModel? = null
    fun resetLoggedPassword(passwordLoggedResetRequest: PasswordLoggedResetRequest?) {
        resetPasswordViewModel!!.postResetLoggedPassword(
            token,
            passwordLoggedResetRequest
        )
    }

    fun setToken(token: String?) {
        this.token = token
    }

    fun setResetPasswordViewModel(resetPasswordViewModel: ResetPasswordViewModel?) {
        this.resetPasswordViewModel = resetPasswordViewModel
    }

    companion object {
        private var securityService: SecurityService? = null
        val instance: SecurityService?
            get() {
                if (securityService == null) {
                    securityService = SecurityService()
                }
                return securityService
            }
    }
}