package com.example.shopkotlin.register.data

import com.example.shopkotlin.register.model.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterInterface {
    @POST("register")
    fun register(@Body registerRequest: RegisterRequest?): Call<Boolean?>?
}