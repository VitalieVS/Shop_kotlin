package com.example.shop_kotlin.login.model

import com.example.shop_kotlin.models.Product
import com.example.shop_kotlin.promotion.model.Promotion
import java.util.*

data class Order (
    var id: Int = 0,
    var shippingAddress: Address? = null,
    var user: List<User>? = null,
    var productList: List<Product>? = null,
    var promotionList: List<Promotion>? = null,
    var totalPrice: Double = 0.0,
    var orderDate: Date? = null,
    var orderStatus: String? = null,
    var imageUrl: String? = null
)