package com.example.shop_kotlin

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shop_kotlin.cart.service.CartService
import com.example.shop_kotlin.category.model.Category
import com.example.shop_kotlin.databinding.ActivityProductBinding
import com.example.shop_kotlin.models.Product
import com.example.shop_kotlin.product.ui.adapter.ProductAdapter

import java.util.*

class ProductActivity : AppCompatActivity() {
    private var cartService: CartService? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        cartService = CartService.instance
        val categoryModel: Category = intent.getSerializableExtra("CategoryData") as Category
        for (product in categoryModel.productList) {
            if (product?.let { cartService!!.productExists(it) }!!) {
                product.let { cartService!!.getProductInCart(it)!!.quantity }
                    .let { product.setQuantity(it) }
            }
        }
        val activityProductBinding: ActivityProductBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_product)
        activityProductBinding.productListRecycler.layoutManager = LinearLayoutManager(this)
        activityProductBinding.productListRecycler.setHasFixedSize(true)
        val productAdapter = ProductAdapter(categoryModel.productList as List<Product>)
        activityProductBinding.productListRecycler.adapter = productAdapter
        val backView: ImageView = findViewById(R.id.backButton)
        backView.setOnClickListener { finish() }
    }
}