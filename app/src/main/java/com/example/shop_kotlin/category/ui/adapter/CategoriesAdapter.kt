package com.example.shop_kotlin.category.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import androidx.recyclerview.widget.RecyclerView
import com.example.shop_kotlin.ProductActivity
import com.example.shop_kotlin.category.model.Category
import com.example.shop_kotlin.databinding.CategoryItemBinding
import java.util.*
import java.util.stream.Collectors

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.CategoryViewHolder?>(), SelectedCategory {
    private var categoryList: List<Category> = ArrayList<Category>()
    private var filteredCategoryList: List<Category> = ArrayList<Category>()
    private var context: Context? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryViewHolder {
        context = parent.context
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val categoryItemBinding: CategoryItemBinding =
            CategoryItemBinding.inflate(layoutInflater, parent, false)
        return CategoryViewHolder(categoryItemBinding)
    }

    override fun onBindViewHolder(
        holder: CategoryViewHolder,
        position: Int
    ) {
        val category: Category = filteredCategoryList[position]
        holder.categoryItemBinding.category = category
        holder.itemView.setOnClickListener { v: View? ->
            selectedCategory(
                category
            )
        }
    }

    override fun getItemCount(): Int {
        return filteredCategoryList.size
    }

    fun setList(categoryList: List<Category>) {
        this.categoryList = categoryList
        filteredCategoryList = categoryList
        notifyDataSetChanged()
    }

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val key = constraint.toString()
                filteredCategoryList = if (key.isEmpty()) {
                    categoryList
                }

                else {
                    removeDuplicates(
                        filterByProduct(key),
                        filterCategories(key)
                    )
                }
                val filterResults = FilterResults()
                filterResults.values = filteredCategoryList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                filteredCategoryList = results.values as List<Category>
                notifyDataSetChanged()
            }
        }
    }

    private fun filterCategories(key: String): MutableList<Category>? {
        return categoryList.stream().filter { item: Category ->
            item.name.lowercase(Locale.getDefault())
                .contains(key.lowercase(Locale.getDefault()))
        }
            .collect(Collectors.toList())
    }

    private fun filterByProduct(key: String): MutableList<Category>? {
        return categoryList.stream().filter { item: Category ->
            item.productList.stream().anyMatch { product ->
                product!!.title.lowercase(Locale.getDefault())
                    .contains(key.lowercase(Locale.getDefault()))
            }
        }
            .collect(Collectors.toList())
    }

    fun removeDuplicates(
        first: MutableList<Category>?,
        second: MutableList<Category>?
    ): List<Category> {


        val clearList: MutableList<Category> = mutableListOf()

        if (first != null) {
            clearList.addAll(first)
        }

        if (second != null) {
            clearList.addAll(second)
        }

        return clearList.distinct()

    }

    class CategoryViewHolder(categoryItemBinding: CategoryItemBinding) :
        RecyclerView.ViewHolder(categoryItemBinding.root) {
        var categoryItemBinding: CategoryItemBinding = categoryItemBinding

    }

    override fun selectedCategory(category: Category?) {
        context!!.startActivity(
            Intent(context, ProductActivity::class.java)
                .putExtra("CategoryData", category)
        )
    }
}