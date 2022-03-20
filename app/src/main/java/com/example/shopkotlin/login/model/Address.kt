package com.example.shopkotlin.login.model

data class Address(
    var id: Int = 0,
    var firstAddress: String? = null,
    var secondAddress: String? = null,
    var city: String? = null,
    var country: String? = null
) {

    fun afterFirstAddressChanged(s: CharSequence) {

        firstAddress = s.toString()
    }

    fun afterSecondAddressChanged(s: CharSequence) {
        secondAddress = s.toString()
    }

    fun afterCityChanged(s: CharSequence) {
        city = s.toString()
    }

    fun afterCountryChanged(s: CharSequence) {
        country = s.toString()
    }
}