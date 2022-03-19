package com.example.shop_kotlin.register.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.shop_kotlin.R
import com.example.shop_kotlin.databinding.FirstRegisterGeneralBinding
import com.example.shop_kotlin.login.model.Address
import com.example.shop_kotlin.register.model.RegisterRequest
import com.example.shop_kotlin.register.service.RegisterService

class GeneralRegisterInfo : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val registerService: RegisterService? = RegisterService.instance
        val firstRegisterGeneralBinding: FirstRegisterGeneralBinding = DataBindingUtil.inflate(
            inflater, R.layout.first_register_general,
            container, false
        )
        registerService?.registerRequest = RegisterRequest()
        registerService?.address = Address()
        firstRegisterGeneralBinding.registerService = registerService
        firstRegisterGeneralBinding.registerRequest = registerService!!.registerRequest
        return firstRegisterGeneralBinding.root
    }
}