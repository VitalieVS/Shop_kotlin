package com.example.shopkotlin.cart.implementation.adapter

import com.example.shopkotlin.cart.binder.ListBindAdapter
import com.example.shopkotlin.cart.implementation.binder.ProductBinder
import com.example.shopkotlin.cart.implementation.binder.PromotionBinder
import com.example.shopkotlin.models.Product
import com.example.shopkotlin.promotion.model.Promotion

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
