package com.example.shopkotlin.category.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopkotlin.category.data.CategoryClient
import com.example.shopkotlin.category.model.Category
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class CategoryViewModel : ViewModel() {
    val categories: Unit
        get() {
            CategoryClient.instance?.categories?.enqueue(object : Callback<List<Category>?> {
                override fun onResponse(
                    call: Call<List<Category>?>,
                    response: Response<List<Category>?>
                ) {
                    for (category in Objects.requireNonNull<List<Category>?>(response.body())) {
                        for (product in category.productList) {
                            product!!.priceCopy = product.price
                        }
                    }
                    categoriesMutableLiveData.value = response.body()
                }

                override fun onFailure(call: Call<List<Category>?>, t: Throwable) {
                    categoriesMutableLiveData.value = EMPTY_LIST
                }
            })

        }

    companion object {
        private val EMPTY_LIST: ArrayList<Category?> = ArrayList<Category?>()
        var categoriesMutableLiveData: MutableLiveData<List<Category?>> =
            MutableLiveData<List<Category?>>()
    }
}
