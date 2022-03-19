package com.example.shop_kotlin.login.data

import com.example.shop_kotlin.login.model.LoginRequest
import com.example.shop_kotlin.login.model.LoginResponse
import com.example.shop_kotlin.login.model.User
import retrofit2.Call
import retrofit2.http.*

interface LoginInterface {
    @POST("login")
    fun login(@Body loginRequest: LoginRequest?): Call<LoginResponse?>?

    @GET("user/{login}")
    fun getUser(
        @Header("Authorization") token: String?,
        @Path("login") login: String?
    ): Call<User?>?
}