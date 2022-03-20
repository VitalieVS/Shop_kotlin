package com.example.shopkotlin.cart.implementation.binder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopkotlin.R
import com.example.shopkotlin.cart.binder.DataBindAdapter
import com.example.shopkotlin.cart.binder.DataBinder
import com.example.shopkotlin.cart.service.CartService
import com.example.shopkotlin.databinding.CartPromotionItemBinding
import com.example.shopkotlin.promotion.model.Promotion
import com.example.shopkotlin.promotion.remover.RemovePromotion

class PromotionBinder(dataBindAdapter: DataBindAdapter?) :
    DataBinder<PromotionBinder.ViewHolder?>(dataBindAdapter), RemovePromotion {
    private var promotionList: List<Promotion> = ArrayList()
    private var position = 0
    private var cartService: CartService? = null


    fun addAll(dataSet: List<Promotion>) {
        promotionList = dataSet
        notifyBinderDataSetChanged()
    }

    override fun removePromotionFromCart(promotion: Promotion?) {
        cartService?.removeFromCart(promotion)
        notifyBinderItemRemoved(position)
    }

    class ViewHolder(promotionItemBinding: CartPromotionItemBinding) :
        RecyclerView.ViewHolder(promotionItemBinding.root) {
        var promotionItemBinding: CartPromotionItemBinding = promotionItemBinding

    }

    override fun newViewHolder(parent: ViewGroup?): ViewHolder? {
        cartService = CartService.instance
        cartService?.setContext(parent?.context)
        val cartItemBinding: CartPromotionItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent!!.context),
            R.layout.cart_promotion_item, parent, false
        )
        return ViewHolder(cartItemBinding)
    }

    override fun bindViewHolder(holder: ViewHolder?, position: Int) {
        this.position = position
        val promotion: Promotion = promotionList[position]
        holder!!.promotionItemBinding.promotion = promotion
        holder.promotionItemBinding.removeInterface = this
        holder.promotionItemBinding.cartService = cartService
    }

    override fun getItemCount(): Int {
       return promotionList.size
    }
}

