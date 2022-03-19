package com.example.shop_kotlin.category.model

import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.shop_kotlin.models.Product
import java.io.Serializable

data class Category (
    var name:String,
    var imageURL:String,
    var productList: List<Product?>

    ) : Serializable