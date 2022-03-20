package com.example.shopkotlin.cart.implementation.binder.remover

import com.example.shopkotlin.models.Product

interface RemoveProduct {
    fun removeProductFromCart(product: Product?)
}
