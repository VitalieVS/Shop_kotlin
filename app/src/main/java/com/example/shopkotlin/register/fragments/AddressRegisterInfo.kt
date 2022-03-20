package com.example.shopkotlin.register.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.shopkotlin.R
import com.example.shopkotlin.databinding.SecondRegisterAddressBinding
import com.example.shopkotlin.register.service.RegisterService

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
        secondRegisterAddressBinding.address = registerService!!.address

        return secondRegisterAddressBinding.root
    }
}