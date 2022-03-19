package com.example.shop_kotlin.cart.implementation.adapter

import com.example.shop_kotlin.cart.binder.ListBindAdapter
import com.example.shop_kotlin.cart.implementation.binder.ProductBinder
import com.example.shop_kotlin.cart.implementation.binder.PromotionBinder
import com.example.shop_kotlin.models.Product
import com.example.shop_kotlin.promotion.model.Promotion

class ProductPromotionList : ListBindAdapter() {

    init {
        addAllBinder(
            ProductBinder(this),
            PromotionBinder(this)
        )
    }

    fun setPromotionDataSet(dataSet: List<Promotion>) {

        getDataBinder<PromotionBinder>(1).addAll(dataSet = dataSet)
    }

    fun setProductDataSet(dataSet: List<Product>) {
        getDataBinder<ProductBinder>(0).addAll(dataSet = dataSet)
    }
}
