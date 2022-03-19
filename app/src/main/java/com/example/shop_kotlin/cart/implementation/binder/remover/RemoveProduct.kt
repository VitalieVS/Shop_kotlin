package com.example.shop_kotlin.cart.implementation.binder.remover

import com.example.shop_kotlin.models.Product

interface RemoveProduct {
    fun removeProductFromCart(product: Product?)
}
