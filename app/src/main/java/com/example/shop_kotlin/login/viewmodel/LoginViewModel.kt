package com.example.shop_kotlin.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shop_kotlin.login.data.LoginClient
import com.example.shop_kotlin.login.model.LoginRequest
import com.example.shop_kotlin.login.model.LoginResponse
import com.example.shop_kotlin.login.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    fun authenticate(loginRequest: LoginRequest?) {
        LoginClient.instance?.login(loginRequest)
            ?.enqueue(object : Callback<LoginResponse?> {
                override fun onResponse(
                    call: Call<LoginResponse?>,
                    response: Response<LoginResponse?>
                ) {
                    if (response.body() != null) {
                        TOKEN.setValue(response.body()!!.token)
                        LOGIN.setValue(response.body()!!.login)
                        LOGIN_STATUS.setValue(AuthorisationStatus.SUCCESS)
                    }
                    if (response.errorBody() != null) {
                        LOGIN.setValue(null)
                        LOGIN_STATUS.setValue(AuthorisationStatus.FAILED)
                        TOKEN.setValue("")
                    }
                }

                override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                    LOGIN.setValue(null)
                    LOGIN_STATUS.setValue(AuthorisationStatus.LOGOUT)
                    TOKEN.setValue("")
                }
            })
    }

    fun getUser(token: String?, user: String?) {
        LoginClient.instance?.getUser(user, token)
            ?.enqueue(object : Callback<User?> {
                override fun onResponse(
                    call: Call<User?>,
                    response: Response<User?>
                ) {
                    if (response.body() != null) {
                        USER_MUTABLE_LIVE_DATA.setValue(response.body())
                        LOGIN_STATUS.setValue(AuthorisationStatus.SUCCESS)
                    } else {
                        USER_MUTABLE_LIVE_DATA.setValue(null)
                        LOGIN_STATUS.setValue(AuthorisationStatus.LOGOUT)
                    }
                }

                override fun onFailure(call: Call<User?>, t: Throwable) {
                    USER_MUTABLE_LIVE_DATA.setValue(null)
                }
            })
    }

    companion object {
        var LOGIN_STATUS: MutableLiveData<AuthorisationStatus> =
            MutableLiveData<AuthorisationStatus>()
        val TOKEN: MutableLiveData<String> = MutableLiveData<String>()
        val USER_MUTABLE_LIVE_DATA: MutableLiveData<User> = MutableLiveData<User>()
        val LOGIN: MutableLiveData<String> = MutableLiveData<String>()
        fun resetStatus() {
            LOGIN_STATUS = MutableLiveData<AuthorisationStatus>()
        }
    }
}