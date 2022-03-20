package com.example.shopkotlin.promotion.data

import com.example.shopkotlin.promotion.model.Promotion
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PromotionsClient {
    private val promotionInterface: PromotionInterface
    val promotions: Call<List<Promotion?>?>?
        get() = promotionInterface.promotions

    companion object {
        private const val BASE_URL =
            "http://10.0.2.2:4546" // This IP address is used only on EMULATOR
        private var INSTANCE: PromotionsClient? = null
        val instance: PromotionsClient?
            get() {
                if (null == INSTANCE) {
                    INSTANCE = PromotionsClient()
                }
                return INSTANCE
            }
    }

    init {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        promotionInterface = retrofit.create(PromotionInterface::class.java)
    }
}
