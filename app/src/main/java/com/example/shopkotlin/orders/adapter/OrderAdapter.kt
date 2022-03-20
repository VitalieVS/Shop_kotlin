package com.example.shopkotlin.orders.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopkotlin.R
import com.example.shopkotlin.databinding.OrderItemBinding
import com.example.shopkotlin.login.model.Order
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

    class OrderViewHolder(var orderItemBinding: OrderItemBinding) :
        RecyclerView.ViewHolder(orderItemBinding.root) {

    }

    init {
        this.orderList = orderList
    }
}