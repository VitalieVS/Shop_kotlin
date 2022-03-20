package com.example.shopkotlin.login.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopkotlin.login.data.LoginClient
import com.example.shopkotlin.login.model.LoginRequest
import com.example.shopkotlin.login.model.LoginResponse
import com.example.shopkotlin.login.model.User
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
                        TOKEN.value = response.body()!!.token
                        LOGIN.value = response.body()!!.login
                        LOGIN_STATUS.value = AuthorisationStatus.SUCCESS
                    }
                    if (response.errorBody() != null) {
                        LOGIN.value = null
                        LOGIN_STATUS.value = AuthorisationStatus.FAILED
                        TOKEN.value = ""
                    }
                }

                override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                    LOGIN.value = null
                    LOGIN_STATUS.value = AuthorisationStatus.LOGOUT
                    TOKEN.value = ""
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


                        println("MIAUNEL")
                        println(response.body()!!.ordersList!!.size)
                        USER_MUTABLE_LIVE_DATA.value = response.body()
                        LOGIN_STATUS.setValue(AuthorisationStatus.SUCCESS)
                    } else {
                        USER_MUTABLE_LIVE_DATA.value = null
                        LOGIN_STATUS.setValue(AuthorisationStatus.LOGOUT)
                    }
                }

                override fun onFailure(call: Call<User?>, t: Throwable) {
                    USER_MUTABLE_LIVE_DATA.value = null
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