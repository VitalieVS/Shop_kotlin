package com.example.shop_kotlin.models

import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.Serializable
import com.example.shop_kotlin.BR

data class Product(
    var id: Int,
    var title: String,
    @Bindable
    var price: Int,
    var priceCopy: Int,
    var imageURL: String,
    @Bindable
    var itemPrice: Int,
    @Bindable
    var quantity: Int,
    var ingredients: List<Ingredient>,
    var weight: Int,
    var vegetarian: Boolean
) : Serializable, BaseObservable() {


    @JvmName("getItemPrice1")
    @Bindable
    fun getItemPrice(): Int {
        return priceCopy * quantity
    }


    fun increaseQuantity() {
        notifyPropertyChanged(BR.quantity)
        notifyPropertyChanged(BR.itemPrice1)
    }

    @JvmName("setQuantity1")
    fun setQuantity(quantity: Int) {
        if (quantity + 1 < 100) {
            this.quantity = quantity
            notifyPropertyChanged(BR.quantity)
        }
    }


    fun decreaseQuantity() {
        notifyPropertyChanged(BR.quantity)
        notifyPropertyChanged(BR.itemPrice1)
    }

    companion object {
        @JvmStatic
        @BindingAdapter("android:background")
        fun loadImage(imageView: ImageView, imageUrl: String?) {
            Glide.with(imageView).load(imageUrl).into(object : CustomTarget<Drawable?>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable?>?
                ) {
                    imageView.background = resource
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    //We don't need to use this implementation
                }
            })
        }
    }

    fun isVegetarian(): String {
        return if (vegetarian) "VEGETARIAN FOOD" else "NON VEGETARIAN FOOD"
    }
}
