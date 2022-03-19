package com.example.shop_kotlin.register.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shop_kotlin.register.data.RegisterClient
import com.example.shop_kotlin.register.model.RegisterRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    fun register(registerRequest: RegisterRequest?) {
        RegisterClient.instance?.register(registerRequest)
            ?.enqueue(object : Callback<Boolean?> {
                override fun onResponse(
                    call: Call<Boolean?>,
                    response: Response<Boolean?>
                ) {
                    if (response.body() != null && response.body()!!) {
                        REGISTER_RESPONSE.setValue(RegisterStatus.REGISTERED)
                    } else {
                        REGISTER_RESPONSE.setValue(RegisterStatus.ERROR)
                    }
                }

                override fun onFailure(call: Call<Boolean?>, t: Throwable) {
                    REGISTER_RESPONSE.setValue(RegisterStatus.NO_INTERNET)
                }
            })
    }

    fun resetResponse() {
        REGISTER_RESPONSE = MutableLiveData<RegisterStatus>()
    }

    companion object {
        var REGISTER_RESPONSE: MutableLiveData<RegisterStatus> = MutableLiveData<RegisterStatus>()
    }
}