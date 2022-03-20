package com.example.shopkotlin.security.reset.logged.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopkotlin.security.reset.anonymous.model.ResetResponse
import com.example.shopkotlin.security.reset.logged.data.ResetLoggedPasswordClient
import com.example.shopkotlin.security.reset.logged.model.PasswordLoggedResetRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResetPasswordViewModel : ViewModel() {
    fun postResetLoggedPassword(
        token: String?,
        passwordLoggedResetRequest: PasswordLoggedResetRequest?
    ) {
        ResetLoggedPasswordClient.instance
            ?.postResetLoggedPassword(token, passwordLoggedResetRequest)
            ?.enqueue(object : Callback<ResetResponse?> {
                override fun onResponse(
                    call: Call<ResetResponse?>,
                    response: Response<ResetResponse?>
                ) {
                    if (response.body() != null) {
                        RESET_RESPONSE.setValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ResetResponse?>, t: Throwable) {
                    RESET_RESPONSE.setValue(null)
                }
            })
    }

    fun setResetResponse() {
        RESET_RESPONSE = MutableLiveData<ResetResponse>()
    }

    companion object {
        var RESET_RESPONSE: MutableLiveData<ResetResponse> = MutableLiveData<ResetResponse>()
    }
}