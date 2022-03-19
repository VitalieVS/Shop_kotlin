package com.example.shop_kotlin.login.data

import com.example.shop_kotlin.login.model.LoginRequest
import com.example.shop_kotlin.login.model.LoginResponse
import com.example.shop_kotlin.login.model.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginClient {
    private val loginInterface: LoginInterface
    fun login(loginRequest: LoginRequest?): Call<LoginResponse?>? {
        return loginInterface.login(loginRequest)
    }

    fun getUser(token: String?, login: String?): Call<User?>? {
        return loginInterface.getUser(login, token)
    }

    companion object {
        private const val BASE_URL =
            "http://10.0.2.2:4546" // This IP address is used only on EMULATOR
        private var INSTANCE: LoginClient? = null
        val instance: LoginClient?
            get() {
                if (null == INSTANCE) {
                    INSTANCE = LoginClient()
                }
                return INSTANCE
            }
    }

    init {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        loginInterface = retrofit.create(LoginInterface::class.java)
    }
}
