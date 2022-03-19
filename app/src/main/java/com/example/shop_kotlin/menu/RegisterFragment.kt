package com.example.shop_kotlin.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.shop_kotlin.R
import com.example.shop_kotlin.databinding.FragmentStepRegisterBinding
import com.example.shop_kotlin.register.adapter.ViewPagerAdapter
import com.example.shop_kotlin.register.fragments.AddressRegisterInfo
import com.example.shop_kotlin.register.fragments.GeneralRegisterInfo
import com.example.shop_kotlin.register.fragments.SecurityRegisterInfo
import com.example.shop_kotlin.register.service.RegisterService
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

class RegisterFragment : Fragment() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentStepRegisterBinding: FragmentStepRegisterBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_step_register, container,
            false
        )
        tabLayout = fragmentStepRegisterBinding.tabLayout.findViewById(R.id.tabLayout)
        viewPager2 = fragmentStepRegisterBinding.viewPager.findViewById(R.id.viewPager)
        viewPager2.offscreenPageLimit = 1
        val adapter = ViewPagerAdapter(
            requireActivity()
                .supportFragmentManager, requireActivity().lifecycle
        )
        adapter.addFragment(GeneralRegisterInfo(), "General")
        adapter.addFragment(AddressRegisterInfo(), "Address")
        adapter.addFragment(SecurityRegisterInfo(), "Security")
        viewPager2.offscreenPageLimit = 1
        viewPager2.adapter = adapter
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        TabLayoutMediator(tabLayout, viewPager2
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = adapter.getFragmentTitleList()[position]
        }.attach()
        for (i in 0 until tabLayout.tabCount) {
            val tv: TextView = LayoutInflater.from(
                fragmentStepRegisterBinding.root.context
            )
                .inflate(R.layout.custom_tab, null) as TextView
            tabLayout.getTabAt(i)?.customView = tv
        }
        val registerService: RegisterService? = RegisterService.instance
        registerService?.setTabLayout(tabLayout)
        registerService?.setContext(context)
        fragmentStepRegisterBinding.registerService = registerService
        return fragmentStepRegisterBinding.root
    }
}