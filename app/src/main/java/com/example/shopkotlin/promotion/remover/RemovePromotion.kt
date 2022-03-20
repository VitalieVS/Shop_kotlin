package com.example.shopkotlin.promotion.remover

import com.example.shopkotlin.promotion.model.Promotion

interface RemovePromotion {
    fun removePromotionFromCart(promotion: Promotion?)
}