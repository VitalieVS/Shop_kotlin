package com.example.shopkotlin.register.service

import android.content.Context
import android.widget.Toast
import com.example.shopkotlin.login.model.Address
import com.example.shopkotlin.register.model.RegisterRequest
import com.example.shopkotlin.register.viewmodel.RegisterViewModel
import com.google.android.material.tabs.TabLayout
import java.util.*
import java.util.function.Predicate
import java.util.regex.Pattern
import java.util.stream.Stream

class RegisterService {
    private val registerViewModel: RegisterViewModel = RegisterViewModel()
    private var tabLayout: TabLayout? = null
    private var context: Context? = null
    lateinit var registerRequest: RegisterRequest
    lateinit var address: Address

    fun setTabLayout(tabLayout: TabLayout?) {
        this.tabLayout = tabLayout
    }

    fun setContext(context: Context?) {
        this.context = context
    }

    fun handleFirstPage() {
        tabLayout?.getTabAt(1)?.select()
    }

    fun handleSecondPage(address: Address?) {
       tabLayout?.getTabAt(2)?.select()
    }

    fun register() {
        if (!validate()) {
            Toast.makeText(context, "Invalid data!", Toast.LENGTH_SHORT).show()
        } else {
            registerViewModel.register(registerRequest)
        }
    }

    private fun validate(): Boolean {

        return true
    }

    companion object {
        private var registerService: RegisterService? = null
        val instance: RegisterService?
            get() {
                if (registerService == null) {
                    registerService = RegisterService()
                }
                return registerService
            }
    }
}