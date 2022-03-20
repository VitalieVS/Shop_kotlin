package com.example.shopkotlin.order.data

import com.example.shopkotlin.order.request.OrderRequest
import com.example.shopkotlin.order.request.OrderResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OrderInterface {
    @POST("createOrder")
    fun createOrder(
        @Header("Authorization") token: String?,
        @Body orderRequest: OrderRequest?
    ): Call<OrderResponse?>?
}