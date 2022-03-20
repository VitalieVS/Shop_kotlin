package com.example.shopkotlin.address.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopkotlin.address.data.AddressClient
import com.example.shopkotlin.address.model.AddressResponse
import com.example.shopkotlin.login.model.Address
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddressViewModel : ViewModel() {
    fun changeAddress(token: String?, address: Address?) {
        AddressClient.instance?.changeAddress(token, address)
            ?.enqueue(object : Callback<AddressResponse?> {
                override fun onResponse(
                    call: Call<AddressResponse?>,
                    response: Response<AddressResponse?>
                ) {
                    ADDRESS_RESPONSE.setValue(response.body())
                }

                override fun onFailure(call: Call<AddressResponse?>, t: Throwable) {
                    ADDRESS_RESPONSE.setValue(null)
                }
            })
    }

    fun resetAddress() {
        ADDRESS_RESPONSE = MutableLiveData<AddressResponse?>()
    }

    companion object {
        var ADDRESS_RESPONSE: MutableLiveData<AddressResponse?> =
            MutableLiveData<AddressResponse?>()
    }
}
