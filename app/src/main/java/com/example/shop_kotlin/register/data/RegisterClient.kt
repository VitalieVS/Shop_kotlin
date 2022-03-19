package com.example.shop_kotlin.register.data

import com.example.shop_kotlin.register.model.RegisterRequest
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterClient {
    private val registerInterface: RegisterInterface
    fun register(registerRequest: RegisterRequest?): Call<Boolean?>? {
        return registerInterface.register(registerRequest)
    }

    companion object {
        private const val BASE_URL =
            "http://10.0.2.2:4546" // This IP address is used only on EMULATOR
        private var INSTANCE: RegisterClient? = null
        val instance: RegisterClient?
            get() {
                if (null == INSTANCE) {
                    INSTANCE = RegisterClient()
                }
                return INSTANCE
            }
    }

    init {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        registerInterface = retrofit.create(RegisterInterface::class.java)
    }
}