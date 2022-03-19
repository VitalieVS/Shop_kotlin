package com.example.shop_kotlin.login.model

import com.example.shop_kotlin.login.model.Order

data class User (
    var surname: String? = null,
    var name: String? = null,
    var address: Address? = null,
    var orders: List<Order>? = null,
    var totalCashBack: Float = 0f
)