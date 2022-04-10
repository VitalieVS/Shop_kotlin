package com.example.shopkotlin.order.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopkotlin.order.data.OrderClient
import com.example.shopkotlin.order.request.OrderRequest
import com.example.shopkotlin.order.request.OrderResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderViewModel : ViewModel() {
    fun createOrder(token: String?, orderRequest: OrderRequest?) {
        OrderClient.INSTANCE.createOrder(token, orderRequest)
            ?.enqueue(object : Callback<OrderResponse?> {
                override fun onResponse(
                    call: Call<OrderResponse?>,
                    response: Response<OrderResponse?>
                ) {

                    ORDER_RESPONSE.value = response.body()
                }

                override fun onFailure(call: Call<OrderResponse?>, t: Throwable) {

                    ORDER_RESPONSE.value = null
                }
            })
    }

    fun resetOrderResponse() {
        ORDER_RESPONSE = MutableLiveData<OrderResponse>()
    }

    companion object {
        var ORDER_RESPONSE: MutableLiveData<OrderResponse> = MutableLiveData<OrderResponse>()
    }
}