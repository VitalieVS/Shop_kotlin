package com.example.shopkotlin.category.model

import com.example.shopkotlin.models.Product
import java.io.Serializable

data class Category (
    var name:String,
    var imageURL:String,
    var productList: List<Product?>

    ) : Serializable