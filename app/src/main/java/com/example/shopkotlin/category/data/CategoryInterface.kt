package com.example.shopkotlin.category.data

import com.example.shopkotlin.category.model.Category
import retrofit2.Call
import retrofit2.http.GET

interface CategoryInterface {
    @get:GET("categories")
    val categories: Call<List<Category>?>?
}