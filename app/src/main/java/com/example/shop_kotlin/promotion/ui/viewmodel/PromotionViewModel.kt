package com.example.shop_kotlin.promotion.ui.viewmodel

import android.R
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shop_kotlin.promotion.data.PromotionsClient
import com.example.shop_kotlin.promotion.model.Promotion
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PromotionViewModel : ViewModel() {
    val promotions: Unit
        get() {
            PromotionsClient.instance?.promotions
                ?.enqueue(object : Callback<List<Promotion?>?> {
                    override fun onResponse(
                        call: Call<List<Promotion?>?>,
                        response: Response<List<Promotion?>?>
                    ) {

                        promotionMutableLiveData.value = response.body()
                    }

                    override fun onFailure(call: Call<List<Promotion?>?>, t: Throwable) {

                        promotionMutableLiveData.value = EMPTY_LIST
                    }
                })
        }

    companion object {
        val promotionMutableLiveData: MutableLiveData<List<Promotion?>?> =
            MutableLiveData<List<Promotion?>?>()
        private val EMPTY_LIST: ArrayList<Promotion?> = ArrayList()
    }
}
