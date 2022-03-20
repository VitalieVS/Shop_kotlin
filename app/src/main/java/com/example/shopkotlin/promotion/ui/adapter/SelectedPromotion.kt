package com.example.shopkotlin.promotion.ui.adapter

import com.example.shopkotlin.promotion.model.Promotion

interface SelectedPromotion {
    fun selectedPromotion(promotionModel: Promotion?)
}