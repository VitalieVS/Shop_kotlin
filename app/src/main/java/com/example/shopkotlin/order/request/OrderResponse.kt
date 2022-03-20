package com.example.shopkotlin.order.request

data class OrderResponse (
    var created: Boolean,
    var date: String,
    var message: String
)