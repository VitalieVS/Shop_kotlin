package com.example.shop_kotlin.product.ingredients.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.shop_kotlin.models.Ingredient
import com.example.shop_kotlin.R
import com.example.shop_kotlin.databinding.IngredientItemBinding

class IngredientAdapter(ingredients: List<Ingredient>) :
    RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder?>() {
    private var ingredientList: List<Ingredient>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val ingredientItemBinding: IngredientItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.ingredient_item, parent, false
        )
        return IngredientViewHolder(ingredientItemBinding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val ingredient: Ingredient = ingredientList[position]
        holder.ingredientItemBinding.ingredient = ingredient
    }

    override fun getItemCount(): Int {
        return ingredientList.size
    }

    fun setList(ingredients: List<Ingredient>) {
        ingredientList = ingredients
    }

    class IngredientViewHolder(ingredientItemBinding: IngredientItemBinding) :
        RecyclerView.ViewHolder(ingredientItemBinding.root) {
        var ingredientItemBinding: IngredientItemBinding = ingredientItemBinding

    }

    init {
        ingredientList = ingredients
    }
}