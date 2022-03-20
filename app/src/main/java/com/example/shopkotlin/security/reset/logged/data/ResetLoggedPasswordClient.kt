package com.example.shopkotlin.security.reset.logged.data


import com.example.shopkotlin.security.reset.anonymous.model.ResetResponse
import com.example.shopkotlin.security.reset.logged.model.PasswordLoggedResetRequest
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ResetLoggedPasswordClient {
    private val resetLoggedPasswordInterface: ResetLoggedPasswordInterface
    fun postResetLoggedPassword(
        token: String?,
        passwordLoggedResetRequest: PasswordLoggedResetRequest?
    ): Call<ResetResponse?>? {
        return resetLoggedPasswordInterface.resetPassword(token, passwordLoggedResetRequest)
    }

    companion object {
        private const val BASE_URL =
            "http://10.0.2.2:4546" // This IP address is used only on EMULATOR
        private var INSTANCE: ResetLoggedPasswordClient? = null
        val instance: ResetLoggedPasswordClient?
            get() {
                if (null == INSTANCE) {
                    INSTANCE = ResetLoggedPasswordClient()
                }
                return INSTANCE
            }
    }

    init {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        resetLoggedPasswordInterface = retrofit.create(
            ResetLoggedPasswordInterface::class.java
        )
    }
}