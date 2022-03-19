package com.example.shop_kotlin.category.data

import com.example.shop_kotlin.category.model.Category
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
        private var INSTANCE: CategoryClient? = null
        val instance: CategoryClient?
            get() {
                if (null == INSTANCE) {
                    INSTANCE = CategoryClient()
                }
                return INSTANCE
            }
    }

    init {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        categoryInterface = retrofit.create(CategoryInterface::class.java)
    }
}
