package com.example.shop_kotlin.menu

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.shop_kotlin.R
import com.example.shop_kotlin.category.model.Category
import com.example.shop_kotlin.category.ui.adapter.CategoriesAdapter
import com.example.shop_kotlin.category.ui.viewmodel.CategoryViewModel

class SearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val categoryViewModel: CategoryViewModel = ViewModelProviders.of(this)[CategoryViewModel::class.java]
        categoryViewModel.categories
        val recyclerView: RecyclerView = requireView().findViewById(R.id.categoryListRecyclerView)
        val adapter = CategoriesAdapter()
        recyclerView.adapter = adapter
        val searchView: EditText = requireView().findViewById(R.id.categoriesSearchView)
        searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // this method is empty because we only use afterTextChanged
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // this method is empty because we only use afterTextChanged
            }

            override fun afterTextChanged(s: Editable) {
                adapter.getFilter().filter(s)
            }
        })
        CategoryViewModel.categoriesMutableLiveData.observe(
            requireActivity()
        ) { categoryModels ->
            adapter.setList(categoryModels as List<Category>)
            if (categoryModels.isEmpty() && isAdded) {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        NoInternetFragment()
                    ).commit()
            }
        }
    }
}