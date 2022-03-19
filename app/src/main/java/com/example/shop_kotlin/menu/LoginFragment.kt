package com.example.shop_kotlin.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shop_kotlin.R
import com.example.shop_kotlin.databinding.BottomSheetForgotPasswordBinding
import com.example.shop_kotlin.databinding.FragmentLoginBinding
import com.example.shop_kotlin.login.model.LoginRequest
import com.example.shop_kotlin.login.service.UserService
import com.example.shop_kotlin.login.viewmodel.AuthorisationStatus
import com.example.shop_kotlin.login.viewmodel.LoginViewModel
import com.example.shop_kotlin.security.reset.anonymous.model.PasswordRequest
import com.example.shop_kotlin.security.reset.anonymous.viewmodel.ResetPasswordViewModel
import com.example.shop_kotlin.security.service.SecurityService
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

class LoginFragment : Fragment() {
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var bindingSheet: BottomSheetForgotPasswordBinding
    private var resetPasswordViewModel: ResetPasswordViewModel? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userService: UserService? = UserService.instance
        userService?.setContext(requireActivity())
        val forgotPassword: TextView = requireActivity().findViewById(R.id.forgotPassword)
        val registerNow: TextView = requireActivity().findViewById(R.id.registerNow)
        registerNow.setOnClickListener {
            val registerFragment: Fragment = RegisterFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true).replace(
                    R.id.fragment_container,
                    registerFragment
                ).commit()
        }
        bottomSheetDialog = BottomSheetDialog(requireActivity(), R.style.BottomSheetDialogTheme)
        val securityService: SecurityService? = SecurityService.instance
        forgotPassword.setOnClickListener {
            bindingSheet = DataBindingUtil.inflate(
                LayoutInflater.from(requireActivity()),
                R.layout.bottom_sheet_forgot_password,
                null,
                false
            )
            bindingSheet.securityService = securityService
            bindingSheet.passwordRequest = PasswordRequest()
            bindingSheet.resetPasswordViewModel = resetPasswordViewModel
            bindingSheet.backButton.setOnClickListener { bottomSheetDialog.cancel() }
            bindingSheet.backText.setOnClickListener { bottomSheetDialog.cancel() }
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
                }
                resetPasswordViewModel!!.setResetResponse()
                bottomSheetDialog.cancel()
            }
            bottomSheetDialog.setContentView(bindingSheet.bottomSheetProductContainer)
            bottomSheetDialog.show()
        }
        LoginViewModel.LOGIN_STATUS.observe(viewLifecycleOwner) { status ->
            if (status.equals(AuthorisationStatus.SUCCESS) && isAdded) {
                userService?.setAuthorised(
                    true, LoginViewModel.TOKEN.value,
                    LoginViewModel.LOGIN.value
                )
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, UserFragment()).commit()
            }
            if (isAdded && status.equals(AuthorisationStatus.FAILED)) {
                Toast.makeText(
                    requireActivity(),
                    "Wrong credentials!", Toast.LENGTH_SHORT
                ).show()
            }
            if (isAdded && status.equals(AuthorisationStatus.LOGOUT)) {
                Toast.makeText(
                    requireActivity(),
                    "Session expired!", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val loginViewModel: LoginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        resetPasswordViewModel = ViewModelProvider(this)[ResetPasswordViewModel::class.java]
        val binding: FragmentLoginBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_login, container, false
        )
        val view: View = binding.root
        val userService: UserService? = UserService.instance
        userService?.setContext(Objects.requireNonNull<ViewGroup?>(container).context)
        binding.loginRequest = LoginRequest()
        binding.loginViewModel = loginViewModel
        binding.userService = userService
        return view
    }
}