package com.example.shopkotlin

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.shopkotlin.cart.service.CartService
import com.example.shopkotlin.databinding.ActivityPromotionItemBinding
import com.example.shopkotlin.promotion.model.Promotion
import java.util.*

class PromotionItemActivity : AppCompatActivity() {
    private var cartService: CartService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val promotionModel: Promotion? =
            intent.getSerializableExtra("SelectedPromotion") as Promotion?
        val binding: ActivityPromotionItemBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_promotion_item)
        binding.promotionModel = promotionModel
        cartService = CartService.instance
        cartService?.setContext(this)
        binding.cartService = cartService
        val backView = findViewById<ImageView>(R.id.backButton)
        backView.setOnClickListener { finish() }
    }
}