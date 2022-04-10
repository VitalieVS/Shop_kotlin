package com.example.shopkotlin.order.data

import com.example.shopkotlin.order.request.OrderRequest
import com.example.shopkotlin.order.request.OrderResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OrderClient {
    private val orderInterface: OrderInterface
    fun createOrder(token: String?, orderRequest: OrderRequest?): Call<OrderResponse?>? {
        return orderInterface.createOrder(token, orderRequest)
    }

    companion object {
        private const val BASE_URL =
            "http://10.0.2.2:4546" // This URL is used by emulator to work with API
        val INSTANCE: OrderClient = OrderClient()
    }

    init {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        orderInterface = retrofit.create(OrderInterface::class.java)
    }
}