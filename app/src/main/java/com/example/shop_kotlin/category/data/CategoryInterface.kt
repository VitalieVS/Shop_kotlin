package com.example.shop_kotlin.category.data

import com.example.shop_kotlin.category.model.Category
import retrofit2.Call
import retrofit2.http.GET

interface CategoryInterface {
    @get:GET("categories")
    val categories: Call<List<Category>?>?
}