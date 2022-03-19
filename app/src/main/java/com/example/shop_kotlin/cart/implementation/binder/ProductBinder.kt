package com.example.shop_kotlin.cart.implementation.binder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingMethod
import androidx.databinding.BindingMethods
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shop_kotlin.R
import com.example.shop_kotlin.cart.binder.DataBindAdapter
import com.example.shop_kotlin.cart.binder.DataBinder
import com.example.shop_kotlin.cart.implementation.binder.remover.RemoveProduct
import com.example.shop_kotlin.cart.service.CartService
import com.example.shop_kotlin.databinding.CartItemBinding
import com.example.shop_kotlin.models.Product
import java.util.*

@BindingMethods(
    BindingMethod(
        type = ImageView::class,
        attribute = "app:srcCompat",
        method = "setImageDrawable"
    )
)
class ProductBinder(dataBindAdapter: DataBindAdapter?) :
    DataBinder<ProductBinder.ViewHolder?>(dataBindAdapter), RemoveProduct {
    private var productList: List<Product> = ArrayList()
    private var cartService: CartService? = null
    private var position = 0


    fun addAll(dataSet: List<Product>) {
        productList = dataSet
        notifyBinderDataSetChanged()
    }

    override fun removeProductFromCart(product: Product?) {
        cartService?.removeFromCart(product)
        notifyBinderItemRemoved(position)
    }

    class ViewHolder(cartItemBinding: CartItemBinding) :
        RecyclerView.ViewHolder(cartItemBinding.root) {
        var cartItemBinding: CartItemBinding = cartItemBinding

    }


    override fun newViewHolder(parent: ViewGroup?): ViewHolder? {
        cartService = CartService.instance
        cartService?.setContext(parent?.context)
        val cartItemBinding: CartItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent!!.context),
            R.layout.cart_item, parent, false
        )
        return ViewHolder(cartItemBinding)
    }


    override fun getItemCount(): Int {
        return productList.size
    }

    override fun bindViewHolder(holder: ViewHolder?, position: Int) {
        this.position = position
        val product: Product = productList[position]
        holder?.cartItemBinding!!.product = product
        holder.cartItemBinding.removeInterface = this
        holder.cartItemBinding.cartService = cartService
    }
}