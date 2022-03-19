package com.example.shop_kotlin.cart.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shop_kotlin.models.Product
import com.example.shop_kotlin.models.State
import com.example.shop_kotlin.promotion.model.Promotion

class CartViewModel : ViewModel() {
    fun setProductLiveDataValue(value: MutableList<Product>) {
        productMutableLiveData.value = value
    }

    fun setPromotionLiveDataValue(value: List<Promotion>) {
        promotionMutableLiveData.value = value
    }

    fun setStateMutableLiveData(value: State) {
        stateMutableLiveData.setValue(value)
    }

    companion object {
        val productMutableLiveData: MutableLiveData<List<Product>> =
            MutableLiveData<List<Product>>()
        val promotionMutableLiveData: MutableLiveData<List<Promotion>> =
            MutableLiveData<List<Promotion>>()
        val stateMutableLiveData: MutableLiveData<State> = MutableLiveData<State>()
    }
}