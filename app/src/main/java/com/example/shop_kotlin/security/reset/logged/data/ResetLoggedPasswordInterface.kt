package com.example.shop_kotlin.security.reset.logged.data

import com.example.shop_kotlin.security.reset.anonymous.model.ResetResponse
import com.example.shop_kotlin.security.reset.logged.model.PasswordLoggedResetRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ResetLoggedPasswordInterface {
    @POST("user/logged/resetPassword")
    fun resetPassword(
        @Header("Authorization") token: String?,
        @Body passwordLoggedResetRequest: PasswordLoggedResetRequest?
    ): Call<ResetResponse?>?
}