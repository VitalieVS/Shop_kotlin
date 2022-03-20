package com.example.shopkotlin.promotion.data

import com.example.shopkotlin.promotion.model.Promotion
import retrofit2.Call
import retrofit2.http.GET

interface PromotionInterface {
    @get:GET("promotions")
    val promotions: Call<List<Promotion?>?>?
}