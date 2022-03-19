package com.example.shop_kotlin.register.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.shop_kotlin.R
import com.example.shop_kotlin.databinding.SecondRegisterAddressBinding
import com.example.shop_kotlin.register.service.RegisterService

class AddressRegisterInfo : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().window
            .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        val registerService: RegisterService? = RegisterService.instance
        val secondRegisterAddressBinding: SecondRegisterAddressBinding = DataBindingUtil.inflate(
            inflater, R.layout.second_register_address,
            container, false
        )
        secondRegisterAddressBinding.registerService = registerService
        if (registerService != null) {
            secondRegisterAddressBinding.address = registerService.address
        }
        return secondRegisterAddressBinding.root
    }
}