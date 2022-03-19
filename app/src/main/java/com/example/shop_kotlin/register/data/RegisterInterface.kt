package com.example.shop_kotlin.register.data

import com.example.shop_kotlin.register.model.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterInterface {
    @POST("register")
    fun register(@Body registerRequest: RegisterRequest?): Call<Boolean?>?
}