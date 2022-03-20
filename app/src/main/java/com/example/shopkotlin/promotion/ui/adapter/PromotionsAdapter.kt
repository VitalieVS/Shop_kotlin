package com.example.shopkotlin.promotion.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopkotlin.PromotionItemActivity
import com.example.shopkotlin.databinding.PromotionItemBinding
import com.example.shopkotlin.promotion.model.Promotion

class PromotionsAdapter : RecyclerView.Adapter<PromotionsAdapter.PromotionViewHolder>(),
    SelectedPromotion {
    private var promotionList: List<Promotion> = ArrayList()
    private var context: Context? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PromotionViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(parent.context)
        val promotionItemBinding: PromotionItemBinding =
            PromotionItemBinding.inflate(layoutInflater, parent, false)
        return PromotionViewHolder(promotionItemBinding)
    }

    override fun onBindViewHolder(
        holder: PromotionViewHolder,
        position: Int
    ) {
        val promotion: Promotion = promotionList[position]
        holder.promotionItemBinding.viewModel = promotion
        holder.itemView.setOnClickListener {
            selectedPromotion(
                promotion
            )
        }
    }

    override fun getItemCount(): Int {
        return promotionList.size
    }

    fun setList(promotionList: MutableList<Promotion>) {

        this.promotionList = promotionList
        notifyDataSetChanged()
    }

    override fun selectedPromotion(promotionModel: Promotion?) {
        context!!.startActivity(
            Intent(context, PromotionItemActivity::class.java)
                .putExtra("SelectedPromotion", promotionModel)
        )
    }

    class PromotionViewHolder(promotionItemBinding: PromotionItemBinding) :
        RecyclerView.ViewHolder(promotionItemBinding.root) {
        var promotionItemBinding: PromotionItemBinding = promotionItemBinding

    }
}