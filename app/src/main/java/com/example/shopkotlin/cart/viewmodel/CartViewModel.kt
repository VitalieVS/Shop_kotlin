package com.example.shopkotlin.cart.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopkotlin.models.Product
import com.example.shopkotlin.models.State
import com.example.shopkotlin.promotion.model.Promotion

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