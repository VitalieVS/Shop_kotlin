package com.example.shopkotlin.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopkotlin.R
import com.example.shopkotlin.orders.adapter.OrderAdapter
import com.example.shopkotlin.databinding.FragmentUserMenuBinding
import com.example.shopkotlin.login.service.UserService
import com.example.shopkotlin.login.viewmodel.LoginViewModel
import java.util.*

class UserFragment : Fragment() {
    private var loginViewModel: LoginViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        val userService: UserService? = UserService.instance
        val binding: FragmentUserMenuBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_user_menu, container, false
        )
        val view: View = binding.root
        userService?.setContext(Objects.requireNonNull<ViewGroup?>(container).context)
        userService?.setFragmentActivity(requireActivity())
        binding.loginViewModel = loginViewModel
        binding.userService = userService
        if (userService != null) {
            loginViewModel!!.getUser(userService.token, userService.login)
        }
        val recyclerView: RecyclerView = view.findViewById(R.id.userOrders)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        LoginViewModel.USER_MUTABLE_LIVE_DATA.observe(viewLifecycleOwner) { user ->
            if (user == null && isAdded) {
                userService!!.logout()
            }
            if (user != null && isAdded) {
                val orderAdapter = user.ordersList?.let { OrderAdapter(it) }
                userService!!.address = user.address
                userService.setCashBack(user.totalCashBack)
                recyclerView.adapter = orderAdapter
                binding.user = user
            }
        }
        return view
    }
}