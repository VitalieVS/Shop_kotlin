package com.example.shop_kotlin.address.data

import com.example.shop_kotlin.address.model.AddressResponse
import com.example.shop_kotlin.login.model.Address
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT

interface AddressInterface {
    @PUT("user/address")
    fun changeAddress(
        @Header("Authorization") token: String?,
        @Body address: Address?
    ): Call<AddressResponse?>?
}
