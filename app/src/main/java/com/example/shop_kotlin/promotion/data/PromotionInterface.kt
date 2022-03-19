package com.example.shop_kotlin.promotion.data

import com.example.shop_kotlin.promotion.model.Promotion
import retrofit2.Call
import retrofit2.http.GET

interface PromotionInterface {
    @get:GET("promotions")
    val promotions: Call<List<Promotion?>?>?
}