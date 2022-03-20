package com.example.shopkotlin.security.reset.logged.data

import com.example.shopkotlin.security.reset.anonymous.model.ResetResponse
import com.example.shopkotlin.security.reset.logged.model.PasswordLoggedResetRequest
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