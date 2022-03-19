package com.example.shop_kotlin.cart.service

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shop_kotlin.cart.viewmodel.CartViewModel
import com.example.shop_kotlin.models.Product
import com.example.shop_kotlin.models.State
import com.example.shop_kotlin.promotion.model.Promotion
import java.util.*
import java.util.function.Predicate

class CartService : BaseObservable() {
    private val productList: MutableList<Product> = ArrayList<Product>()
    private val promotionList: MutableList<Promotion> = ArrayList<Promotion>()
    private var cartViewModel: CartViewModel? = null
    private var fragmentActivity: FragmentActivity? = null
    private var totalCartPrice = 0
    fun bottomSheetAddToProductCart(product: Product) {
        if (productExists(product)) {
            val productInCart: Optional<Product?> = productList.stream()
                .filter { item: Product? -> item!!.id == product.id }
                .findFirst()
            Objects.requireNonNull<Product?>(productInCart.orElse(null))
                .setQuantity(product.quantity)
        } else {
            addToProductCart(null, product)
        }
    }

    fun addToProductCart(view: View?, product: Product) {
        if (productExists(product)) {
            Toast.makeText(
                view!!.context, "Product already in cart, please change quantity!",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            productList.add(product)
            cartViewModel?.setProductLiveDataValue(productList)
            cartViewModel?.setStateMutableLiveData(State.PRODUCT_ITEMS)
        }
    }

    fun increaseProductQuantity(product: Product) {
        if (product.quantity + 1 < 100) {
            product.setQuantity(product.quantity + 1)
            product.increaseQuantity()
            notifyPropertyChanged(BR.totalCartPrice)
        }
    }

    fun decreaseProductQuantity(product: Product) {
        if (product.quantity - 1 > 0) {
            product.setQuantity(product.quantity - 1)
            product.decreaseQuantity()
            notifyPropertyChanged(BR.totalCartPrice)
        }
    }

    fun addToPromotionsCart(view: View, promotionModel: Promotion) {
        if (promotionExists(promotionModel)) {
            Toast.makeText(
                view.context, "Can't add more than 1 promotional item!",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            promotionList.add(promotionModel)
            cartViewModel?.setPromotionLiveDataValue(promotionList)
            cartViewModel?.setStateMutableLiveData(State.PROMOTION_ITEMS)
        }
    }

    fun <T> removeFromCart(item: T) {
        if (item is Promotion) {
            promotionList.removeIf { x: Promotion -> x.id == (item as Promotion).id }
        } else {
            productList.removeIf { x: Product -> x.id == (item as Product).id }
        }
        val map: MutableMap<Boolean, Runnable> = HashMap()
        map[promotionList.isNotEmpty()] =
            Runnable { cartViewModel?.setStateMutableLiveData(State.PROMOTION_ITEMS) }
        map[productList.isNotEmpty()] =
            Runnable { cartViewModel?.setStateMutableLiveData(State.PRODUCT_ITEMS) }
        val defaultAction = Runnable { cartViewModel?.setStateMutableLiveData(State.EMPTY_CART) }
        Objects.requireNonNull(
            map.getOrDefault(
                true,
                defaultAction
            )
        ).run()
        cartViewModel?.setProductLiveDataValue(productList)
        cartViewModel?.setPromotionLiveDataValue(promotionList)
        notifyPropertyChanged(BR.totalCartPrice)
    }

    private fun promotionExists(promotionModel: Promotion): Boolean {
        return promotionList.stream().anyMatch { item: Promotion -> item.id == promotionModel.id }
    }

    fun productExists(product: Product): Boolean {
        return productList.stream()
            .anyMatch { item: Product? -> item!!.id == product.id }
    }

    fun getProductInCart(product: Product): Product? {
        return productList.stream().filter { item: Product? -> item!!.id == product.id }.findFirst()
            .orElse(null)
    }

    fun setContext(context: Context?) {
        cartViewModel =
            (context as FragmentActivity?)?.let { ViewModelProvider(it) }?.get(CartViewModel::class.java)
    }

    fun getProductList(): List<Product?> {
        return productList
    }

    fun getPromotionList(): List<Promotion> {
        return promotionList
    }

    @Bindable
    fun getTotalCartPrice(): Int {
        return promotionList.stream().reduce(0,
            { x: Int, y: Promotion -> x + y.price },
            { a: Int, b: Int -> Integer.sum(a, b) }) + productList.stream().reduce(0,
            { x: Int, y: Product? -> x + y!!.price * (y.quantity  ) },
            { a: Int, b: Int -> Integer.sum(a, b) })
    }

    fun setTotalCartPrice(totalCartPrice: Int) {
        this.totalCartPrice = totalCartPrice
    }

    fun setFragmentActivity(fragmentActivity: FragmentActivity?) {
        this.fragmentActivity = fragmentActivity
    }

    fun resetCart() {
        productList.clear()
        promotionList.clear()
        totalCartPrice = 0
        cartViewModel?.setStateMutableLiveData(State.EMPTY_CART)
    }

    companion object {
        private var cartService: CartService? = null
        val instance: CartService?
            get() {
                if (cartService == null) {
                    cartService = CartService()
                }
                return cartService
            }
    }
}