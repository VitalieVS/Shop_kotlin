package com.example.shop_kotlin.security.reset.anonymous.data

import com.example.shop_kotlin.security.reset.anonymous.model.ResetResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface ResetPasswordInterface {
    @POST("reset/password")
    fun resetPassword(@Query("email") email: String?): Call<ResetResponse?>?
}