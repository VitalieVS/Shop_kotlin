package com.example.shopkotlin.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopkotlin.R
import com.example.shopkotlin.promotion.model.Promotion
import com.example.shopkotlin.promotion.ui.adapter.PromotionsAdapter
import com.example.shopkotlin.promotion.ui.viewmodel.PromotionViewModel

class HomeFragment : Fragment() {
    private var promotionViewModel: PromotionViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        promotionViewModel = ViewModelProvider(this).get(PromotionViewModel::class.java)
        promotionViewModel!!.promotions
        val recyclerView: RecyclerView = requireView().findViewById(R.id.promotionRecycler)
        val adapter = PromotionsAdapter()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        promotionViewModel!!.promotions
        PromotionViewModel.promotionMutableLiveData.observe(requireActivity()) { promotionModels ->

            adapter.setList(promotionModels as MutableList<Promotion>)
            if (promotionModels.isEmpty() && isAdded) {
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, NoInternetFragment()).commit()
                }
        }
    }
}