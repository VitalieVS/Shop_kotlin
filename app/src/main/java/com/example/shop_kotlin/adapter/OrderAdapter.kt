package com.example.shop_kotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shop_kotlin.R
import com.example.shop_kotlin.databinding.OrderItemBinding
import com.example.shop_kotlin.login.model.Order
import java.util.ArrayList

class OrderAdapter(orderList: List<Order>) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder?>() {
    private var orderList: List<Order> = ArrayList<Order>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val orderItemBinding: OrderItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.order_item, parent, false
        )
        return OrderViewHolder(orderItemBinding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order: Order = orderList[position]
        holder.orderItemBinding.order = order
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    fun setOrderList(orderList: List<Order>) {
        this.orderList = orderList
    }

    class OrderViewHolder(var orderItemBinding: OrderItemBinding) :
        RecyclerView.ViewHolder(orderItemBinding.root) {

    }

    init {
        this.orderList = orderList
    }
}