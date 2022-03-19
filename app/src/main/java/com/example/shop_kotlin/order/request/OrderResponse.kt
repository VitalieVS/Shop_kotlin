package com.example.shop_kotlin.order.request

data class OrderResponse (
    var created: Boolean,
    var date: String,
    var message: String
)