package com.example.shopkotlin.promotion.model

import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.Serializable

data class Promotion(
    var id: Int,
    var title: String, var body: String,
    var image: String, var price: Int, var foodType: String
) : Serializable {


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

}
