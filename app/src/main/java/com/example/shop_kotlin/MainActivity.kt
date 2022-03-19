package com.example.shop_kotlin

import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.shop_kotlin.login.service.UserService
import com.example.shop_kotlin.menu.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.activity_main)

        var userService: UserService? = null

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.selectedItemId = R.id.home

        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container, HomeFragment()
        ).commit()

        userService = UserService.instance
        userService?.setContext(this)

        bottomNavigationView.setOnItemSelectedListener { item: MenuItem ->
            val selectedFragment: Fragment
            when (item.itemId) {
                R.id.home -> selectedFragment = HomeFragment()
                R.id.search -> selectedFragment = SearchFragment()
                R.id.login -> selectedFragment = if (userService!!.isAuthorised && userService.rememberMe) {
                    UserFragment()
                } else {
                    LoginFragment()
                }
                R.id.cart -> {
                    supportFragmentManager.beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.fragment_container, CartFragment::class.java, null).commit()
                    selectedFragment = CartFragment()
                }
                else -> throw UnknownError("Unknown value")
            }
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true).replace(
                    R.id.fragment_container,
                    selectedFragment
                ).commit()
            true
        }

    }
}