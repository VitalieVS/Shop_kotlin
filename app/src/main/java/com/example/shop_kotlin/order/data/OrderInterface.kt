package com.example.shop_kotlin.order.data

import com.example.shop_kotlin.order.request.OrderRequest
import com.example.shop_kotlin.order.request.OrderResponse
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