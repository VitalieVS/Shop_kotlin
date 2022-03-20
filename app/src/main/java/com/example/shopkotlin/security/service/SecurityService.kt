package com.example.shopkotlin.security.service

import com.example.shopkotlin.security.reset.logged.model.PasswordLoggedResetRequest
import com.example.shopkotlin.security.reset.logged.viewmodel.ResetPasswordViewModel

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