package com.example.shopkotlin.address.data

import com.example.shopkotlin.address.model.AddressResponse
import com.example.shopkotlin.login.model.Address
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddressClient {
    private val categoryInterface: AddressInterface
    fun changeAddress(token: String?, address: Address?): Call<AddressResponse?>? {
        return categoryInterface.changeAddress(token, address)
    }

    companion object {
        private const val BASE_URL =
            "http://10.0.2.2:4546" // This URL is used by emulator to work with API
        private var INSTANCE: AddressClient? = null
        val instance: AddressClient?
            get() {
                if (null == INSTANCE) {
                    INSTANCE = AddressClient()
                }
                return INSTANCE
            }
    }

    init {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        categoryInterface = retrofit.create(AddressInterface::class.java)
    }
}