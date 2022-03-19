package com.example.shop_kotlin.security.reset.logged.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shop_kotlin.R
import com.example.shop_kotlin.databinding.FragmentLoggedResetPasswordBinding
import com.example.shop_kotlin.menu.UserFragment

import com.example.shop_kotlin.security.reset.logged.model.PasswordLoggedResetRequest
import com.example.shop_kotlin.security.reset.logged.viewmodel.ResetPasswordViewModel
import com.example.shop_kotlin.security.service.SecurityService


class LoggedResetPassword : Fragment() {
    private var resetPasswordViewModel: ResetPasswordViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        resetPasswordViewModel = ViewModelProvider(this)[ResetPasswordViewModel::class.java]
        val securityService: SecurityService? = SecurityService.instance
        securityService?.setResetPasswordViewModel(resetPasswordViewModel)
        val binding: FragmentLoggedResetPasswordBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_logged_reset_password, container, false
        )
        val view: View = binding.root
        val settings: SharedPreferences =
            requireContext().getSharedPreferences(Context.ACCOUNT_SERVICE, 0)
        securityService?.setToken("Bearer_" + settings.getString("token", ""))
        binding.resetRequest = PasswordLoggedResetRequest()
        binding.securityService = securityService
        ResetPasswordViewModel.RESET_RESPONSE.observe(
            viewLifecycleOwner
        ) { resetResponse ->
            if (resetResponse == null && isAdded) {
                Toast.makeText(
                    requireActivity(), "No internet!",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (isAdded) {
                Toast.makeText(
                    requireActivity(),
                    resetResponse.message, Toast.LENGTH_SHORT
                ).show()
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, UserFragment()).commit()
            }
            resetPasswordViewModel!!.setResetResponse()
        }
        val backView = view.findViewById<ImageView>(R.id.backButton)
        backView.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, UserFragment()).commit()
        }
        return view
    }
}