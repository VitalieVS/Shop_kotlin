package com.example.shop_kotlin.order.data

import com.example.shop_kotlin.order.request.OrderRequest
import com.example.shop_kotlin.order.request.OrderResponse
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
        private var INSTANCE: OrderClient? = null
        val instance: OrderClient?
            get() {
                if (null == INSTANCE) {
                    INSTANCE = OrderClient()
                }
                return INSTANCE
            }
    }

    init {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        orderInterface = retrofit.create<OrderInterface>(OrderInterface::class.java)
    }
}