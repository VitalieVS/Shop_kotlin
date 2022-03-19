package com.example.shop_kotlin.register.model

import com.example.shop_kotlin.login.model.Address


class RegisterRequest {
    lateinit var name: String
    lateinit var surname: String
    lateinit var email: String
    lateinit var phone: String
    lateinit var password: String
    lateinit var address: Address
    lateinit var secondPassword: String

    constructor() {}
    constructor(
        name: String, surname: String, email: String,
        password: String, address: Address,
        phone: String
    ) {
        this.name = name
        this.surname = surname
        this.email = email
        this.password = password
        this.address = address
        this.phone = phone
    }

    fun afterNameChanged(s: CharSequence) {
        name = s.toString()
    }

    fun afterSurnameChanged(s: CharSequence) {
        surname = s.toString()
    }

    fun afterEmailChanged(s: CharSequence) {
        email = s.toString()
    }

    fun afterPasswordChanged(s: CharSequence) {
        password = s.toString()
    }

    fun afterPhoneChanged(s: CharSequence) {
        phone = s.toString()
    }

    fun afterSecondPasswordChanged(s: CharSequence) {
        secondPassword = s.toString()
    }
}