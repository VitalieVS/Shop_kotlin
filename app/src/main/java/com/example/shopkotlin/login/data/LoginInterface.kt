package com.example.shopkotlin.login.data

import com.example.shopkotlin.login.model.LoginRequest
import com.example.shopkotlin.login.model.LoginResponse
import com.example.shopkotlin.login.model.User
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