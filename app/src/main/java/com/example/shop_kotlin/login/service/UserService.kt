package com.example.shop_kotlin.login.service

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.FragmentActivity
import com.example.shop_kotlin.R
import com.example.shop_kotlin.address.LoggedAddressFragment
import com.example.shop_kotlin.address.viewmodel.AddressViewModel
import com.example.shop_kotlin.login.model.Address
import com.example.shop_kotlin.login.model.User
import com.example.shop_kotlin.login.viewmodel.LoginViewModel
import com.example.shop_kotlin.menu.LoginFragment
import com.example.shop_kotlin.security.reset.logged.fragment.LoggedResetPassword

class UserService {
    private var context: Context? = null
    private var fragmentActivity: FragmentActivity? = null
    private var addressViewModel: AddressViewModel? = null
    var address: Address? = null
    fun setFragmentActivity(fragmentActivity: FragmentActivity?) {
        this.fragmentActivity = fragmentActivity
    }

    val rememberMe: Boolean
        get() {
            val settings: SharedPreferences =
                context!!.getSharedPreferences(Context.ACCOUNT_SERVICE, 0)
            return settings.getBoolean("remember", false)
        }

    fun setRememberMe(rememberMe: Boolean, i: Int) {
        val settings: SharedPreferences = context!!.getSharedPreferences(Context.ACCOUNT_SERVICE, 0)
        val editor: SharedPreferences.Editor = settings.edit()
        editor.putBoolean("remember", rememberMe)
        editor.apply()
    }

    fun setAuthorised(authorised: Boolean, token: String?, login: String?) {
        val settings: SharedPreferences = context!!.getSharedPreferences(Context.ACCOUNT_SERVICE, 0)
        val editor: SharedPreferences.Editor = settings.edit()
        editor.putString("login", login)
        editor.putBoolean("authorized", authorised)
        editor.putString("token", token)
        editor.apply()
    }

    fun setCashBack(cashback: Float) {
        val settings: SharedPreferences = context!!.getSharedPreferences(Context.ACCOUNT_SERVICE, 0)
        val editor: SharedPreferences.Editor = settings.edit()
        editor.putFloat("cashback", cashback)
        editor.apply()
    }

    val cashback: Float
        get() {
            val settings: SharedPreferences =
                context!!.getSharedPreferences(Context.ACCOUNT_SERVICE, 0)
            return settings.getFloat("cashback", 0f)
        }

    fun openResetPassword() {
        fragmentActivity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragment_container, LoggedResetPassword())?.commit()
    }

    fun openAddress(user: User) {
        user.address?.let { LoggedAddressFragment(it) }?.let {
            fragmentActivity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragment_container, it)?.commit()
        }
    }

    fun changeAddress(address: Address?) {
        addressViewModel?.changeAddress(token, address)
    }

    val token: String
        get() {
            val settings: SharedPreferences =
                context!!.getSharedPreferences(Context.ACCOUNT_SERVICE, 0)
            return "Bearer_" + settings.getString("token", "")
        }

    fun logout() {
        val settings: SharedPreferences = context!!.getSharedPreferences(Context.ACCOUNT_SERVICE, 0)
        val editor: SharedPreferences.Editor = settings.edit()
        editor.remove("login")
        editor.remove("authorized")
        editor.remove("token")
        editor.putBoolean("remember", false)
        editor.apply()
        LoginViewModel.resetStatus()
        fragmentActivity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragment_container, LoginFragment())?.commit()
    }

    val login: String?
        get() {
            val settings: SharedPreferences =
                context!!.getSharedPreferences(Context.ACCOUNT_SERVICE, 0)
            return settings.getString("login", "")
        }
    val isAuthorised: Boolean
        get() {
            val settings: SharedPreferences =
                context!!.getSharedPreferences(Context.ACCOUNT_SERVICE, 0)
            return settings.getBoolean("authorized", false)
        }

    fun setContext(context: Context?) {
        this.context = context
    }

    fun setAddressViewModel(addressViewModel: AddressViewModel?) {
        this.addressViewModel = addressViewModel
    }

    companion object {
        private val INSTANCE: UserService? = null
        val instance: UserService?
            get() = INSTANCE ?: UserService()
    }
}