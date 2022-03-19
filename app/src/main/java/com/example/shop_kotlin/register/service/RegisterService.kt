package com.example.shop_kotlin.register.service

import android.content.Context
import android.widget.Toast
import com.example.shop_kotlin.login.model.Address
import com.example.shop_kotlin.register.model.RegisterRequest
import com.example.shop_kotlin.register.viewmodel.RegisterViewModel
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
    var address: Address = Address()

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
//        val email =
//            "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"
//        var validatedNames = false
//        var validatedMail = false
//        var validatedPhone = false
//        var validatedPassword = false
//        var validatedAddress = false
//        if (Stream.of(
//                registerRequest.name,
//                registerRequest.surname,
//                registerRequest.email,
//                registerRequest.secondPassword,
//                registerRequest.password,
//                registerRequest.phone
//            )
//                .allMatch(Predicate<T> { obj: T? -> Objects.nonNull(obj) })
//        ) {
//            validatedNames = (registerRequest.getName().length() > 3
//                    && registerRequest.getSurname().length() > 3)
//            val emailPattern = Pattern.compile(email)
//            val emailMatcher = emailPattern.matcher(registerRequest.getEmail())
//            val phonePattern = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+$")
//            val phoneMatcher = phonePattern.matcher(registerRequest.getPhone())
//            validatedMail = emailMatcher.matches()
//            validatedAddress = registerRequest.getAddress().getFirstAddress()
//                .length() > 2 && registerRequest.getAddress().getSecondAddress()
//                .length() > 1 && registerRequest.getAddress().getCity()
//                .length() > 3 && registerRequest.getAddress().getCountry().length() > 3
//            validatedPhone = (phoneMatcher.matches() && registerRequest.getPhone().length() === 8
//                    || registerRequest.getPhone().length() === 7)
//            validatedPassword = (registerRequest.getPassword()
//                .equals(registerRequest.getSecondPassword())
//                    && registerRequest.getPassword().length() > 5)
        //}
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