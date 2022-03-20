package com.example.shopkotlin.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shopkotlin.R
import com.example.shopkotlin.address.viewmodel.AddressViewModel
import com.example.shopkotlin.databinding.FragmentLoggedAddressBinding
import com.example.shopkotlin.login.model.Address
import com.example.shopkotlin.login.service.UserService
import com.example.shopkotlin.menu.NoInternetFragment
import com.example.shopkotlin.menu.UserFragment

class LoggedAddressFragment(address: Address) : Fragment() {
    private val address: Address = address
    private var addressViewModel: AddressViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addressViewModel = ViewModelProvider(requireActivity())[AddressViewModel::class.java]
        val binding: FragmentLoggedAddressBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_logged_address, container, false
        )
        val userService: UserService? = UserService.instance
        val view: View = binding.root
        userService?.setContext(requireContext())
        userService?.setAddressViewModel(addressViewModel)
        binding.address = address
        binding.userService = userService
        val backView = view.findViewById<ImageView>(R.id.backButton)
        backView.setOnClickListener { v: View? ->
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, UserFragment()).commit()
        }
        AddressViewModel.ADDRESS_RESPONSE.observe(
            viewLifecycleOwner
        ) { addressResponse ->
            if (addressResponse == null && isAdded) {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, NoInternetFragment()).commit()
            } else if (addressResponse != null) {
                if (isAdded && addressResponse.error.contains("Succ")) {
                    Toast.makeText(
                                requireActivity(),
                                addressResponse.message, Toast.LENGTH_SHORT
                            ).show()
                    requireActivity().supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_container, UserFragment()).commit()
                } else if (isAdded) {
                    Toast.makeText(
                        requireActivity(),
                        addressResponse.message, Toast.LENGTH_SHORT
                    ).show()
                }
            }
            addressViewModel!!.resetAddress()
        }
        return view
    }

}