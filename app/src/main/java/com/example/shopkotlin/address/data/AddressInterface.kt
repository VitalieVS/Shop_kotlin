package com.example.shopkotlin.address.data

import com.example.shopkotlin.address.model.AddressResponse
import com.example.shopkotlin.login.model.Address
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
