package com.example.shopkotlin.category.data

import com.example.shopkotlin.category.model.Category
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CategoryClient {
    private val categoryInterface: CategoryInterface
    val categories: Call<List<Category>?>?
        get() = categoryInterface.categories

    companion object {
        private const val BASE_URL =
            "http://10.0.2.2:4546" // This URL is used by emulator to work with API
        var INSTANCE: CategoryClient = CategoryClient()
    }

    init {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        categoryInterface = retrofit.create(CategoryInterface::class.java)
    }
}
