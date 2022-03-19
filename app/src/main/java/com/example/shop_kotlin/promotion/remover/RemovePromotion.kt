package com.example.shop_kotlin.promotion.remover

import com.example.shop_kotlin.promotion.model.Promotion

interface RemovePromotion {
    fun removePromotionFromCart(promotion: Promotion?)
}