package com.example.shop_kotlin.register.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shop_kotlin.R
import com.example.shop_kotlin.databinding.ThirdRegisterSecurityBinding
import com.example.shop_kotlin.menu.NoInternetFragment
import com.example.shop_kotlin.register.service.RegisterService
import com.example.shop_kotlin.register.viewmodel.RegisterStatus
import com.example.shop_kotlin.register.viewmodel.RegisterViewModel

class SecurityRegisterInfo : Fragment() {
    private var registerViewModel: RegisterViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerViewModel = ViewModelProvider(this)[RegisterViewModel::class.java]
        val registerService: RegisterService? = RegisterService.instance
        val thirdRegisterSecurityBinding: ThirdRegisterSecurityBinding = DataBindingUtil.inflate(
            inflater, R.layout.third_register_security,
            container, false
        )
        thirdRegisterSecurityBinding.registerService = registerService
        thirdRegisterSecurityBinding.registerRequest = registerService?.registerRequest
        return thirdRegisterSecurityBinding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        RegisterViewModel.REGISTER_RESPONSE.observe(viewLifecycleOwner) { response ->
            if (response.equals(RegisterStatus.NO_INTERNET) && isAdded) {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        NoInternetFragment()
                    ).commit()
            }
            if (response.equals(RegisterStatus.ERROR) && isAdded) {
                Toast.makeText(
                    view.context,
                    "User already exists", Toast.LENGTH_SHORT
                ).show()
            }
            if (response.equals(RegisterStatus.REGISTERED) && isAdded) {
                registerViewModel?.resetResponse()
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.fragment_container,
                        SuccessRegister()
                    ).commit()
            }
        }
    }
}