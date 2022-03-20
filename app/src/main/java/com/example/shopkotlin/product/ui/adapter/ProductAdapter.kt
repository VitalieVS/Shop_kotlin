package com.example.shopkotlin.product.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shopkotlin.R
import com.example.shopkotlin.cart.service.CartService
import com.example.shopkotlin.databinding.BottomSheetItemBinding
import com.example.shopkotlin.databinding.ProductItemBinding
import com.example.shopkotlin.models.Product
import com.example.shopkotlin.product.ingredients.ui.IngredientAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

class ProductAdapter(productList: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder?>() {
    private lateinit var context: Context
    private val productList: List<Product> = productList
    private lateinit var bindingSheet: BottomSheetItemBinding
    private var bottomSheetDialog: BottomSheetDialog? = null
    private var ingredientAdapter: IngredientAdapter? = null
    private var cartService: CartService? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {
        context = parent.context
        cartService = CartService.instance
        cartService?.setContext(parent.context)
        val productItemBinding: ProductItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.product_item, parent, false
            )
        bottomSheetDialog = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)
        return ProductViewHolder(productItemBinding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product: Product = productList[position]
        holder.productItemBinding.product = product
        holder.productItemBinding.cartService = cartService
        holder.itemView.setOnClickListener {
            bindingSheet = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.bottom_sheet_item,
                null,
                false
            )
            bindingSheet.product = product
            bindingSheet.cartService = cartService
            bottomSheetDialog?.setContentView(bindingSheet.bottomSheetProductContainer)
            val recyclerView1: RecyclerView = bindingSheet.ingredientRecyclerView
            ingredientAdapter = IngredientAdapter(product.ingredients)
            recyclerView1.adapter = ingredientAdapter
            bottomSheetDialog?.show()
        }
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    class ProductViewHolder(productItemBinding: ProductItemBinding) :
        RecyclerView.ViewHolder(productItemBinding.root) {
        var productItemBinding: ProductItemBinding = productItemBinding

    }

}