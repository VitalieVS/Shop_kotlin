package com.example.shopkotlin.login.model

data class User (
    var surname: String? = null,
    var name: String? = null,
    var address: Address? = null,
    var ordersList: List<Order>? = null,
    var totalCashBack: Float = 0f
)