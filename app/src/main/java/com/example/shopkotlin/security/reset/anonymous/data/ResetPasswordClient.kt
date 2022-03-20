package com.example.shopkotlin.security.reset.anonymous.data

import com.example.shopkotlin.security.reset.anonymous.model.ResetResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ResetPasswordClient {
    private val resetPasswordInterface: ResetPasswordInterface
    fun postReset(email: String?): Call<ResetResponse?>? {
        return resetPasswordInterface.resetPassword(email)
    }

    companion object {
        private const val BASE_URL =
            "http://10.0.2.2:4546" // This IP address is used only on EMULATOR
        private var INSTANCE: ResetPasswordClient? = null
        val instance: ResetPasswordClient?
            get() {
                if (null == INSTANCE) {
                    INSTANCE = ResetPasswordClient()
                }
                return INSTANCE
            }
    }

    init {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        resetPasswordInterface =
            retrofit.create(ResetPasswordInterface::class.java)
    }
}