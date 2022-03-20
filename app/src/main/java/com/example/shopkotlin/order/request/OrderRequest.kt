package com.example.shopkotlin.order.request

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.shopkotlin.models.Product
import com.example.shopkotlin.promotion.model.Promotion
import com.example.shopkotlin.BR

class OrderRequest(
    productList: List<Product>,
    promotionList: List<Promotion>
) : BaseObservable() {
    private val orderStatus = "CREATED"
    private var productList: List<Product>
    var paymentMethod: String? = null
    private var promotionList: List<Promotion>

    @get:Bindable
    var cashBackApplied = 0f
        set(cashBackApplied) {
            field = cashBackApplied
            notifyPropertyChanged(BR.cashBackApplied)
        }

    fun getProductList(): List<Product> {
        return productList
    }

    fun setProductList(productList: List<Product>) {
        this.productList = productList
    }

    fun getPromotionList(): List<Promotion> {
        return promotionList
    }

    fun setPromotionList(promotionList: List<Promotion>) {
        this.promotionList = promotionList
    }

    init {
        this.productList = productList
        this.promotionList = promotionList
    }
}