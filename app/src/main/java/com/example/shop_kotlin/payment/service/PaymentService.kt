package com.example.shop_kotlin.payment.service

import android.content.Context
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.shop_kotlin.login.service.UserService
import com.example.shop_kotlin.order.request.OrderRequest
import com.example.shop_kotlin.order.ui.OrderViewModel
import com.example.shop_kotlin.payment.paypal.Config
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.createorder.CreateOrder
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.OrderIntent
import com.paypal.checkout.createorder.UserAction
import com.paypal.checkout.order.Amount
import com.paypal.checkout.order.AppContext
import com.paypal.checkout.order.Order
import com.paypal.checkout.order.PurchaseUnit
import com.paypal.checkout.paymentbutton.PayPalButton
import java.util.*


class PaymentService {
    private var paymentMethod: String? = null
    private var totalPrice: Float = 0F
    private var context: Context? = null
    private var fragmentActivity: FragmentActivity? = null
    private var orderViewModel: OrderViewModel? = null
    private var token: String? = null
    fun setContext(context: Context?) {
        this.context = context
    }

    fun setFragmentActivity(fragmentActivity: FragmentActivity?) {
        this.fragmentActivity = fragmentActivity
    }

    fun paymentChanged(
        buttonView: CompoundButton, isChecked: Boolean,
        orderRequest: OrderRequest
    ) {
        if (isChecked) {
            paymentMethod = buttonView.text as String?
            orderRequest.paymentMethod = paymentMethod
        }
    }

    fun applyCashback(
        buttonView: CompoundButton, isChecked: Boolean,
        orderRequest: OrderRequest
    ) {
        if (isChecked) {
            val userService: UserService? = UserService.instance
            userService?.setContext(buttonView.context)
            if (totalPrice * 0.30 >= userService!!.cashback) {
                orderRequest.cashBackApplied = userService.cashback
                totalPrice -= userService.cashback
            } else {
                val orderCashBackMax = totalPrice * 0.30 + totalPrice
                orderRequest.cashBackApplied = (orderCashBackMax - totalPrice).toFloat()
                totalPrice -= (orderCashBackMax - totalPrice).toFloat()
            }
        } else {
            orderRequest.cashBackApplied = 0F
        }
    }

    fun pay(view: View, orderRequest: OrderRequest?) {
        if (paymentMethod == null) {
            Toast.makeText(
                view.context, "Please select a payment method!",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            orderViewModel?.createOrder(token, orderRequest)
        }
    }

    fun setTotalPrice(totalPrice: Int) {
        this.totalPrice = totalPrice.toFloat()
    }

    fun setupPayPal(
        payPalButton: PayPalButton, bottomSheetDialog: BottomSheetDialog,
        orderRequest: OrderRequest
    ) {
        val checkoutConfig = fragmentActivity?.let {
            CheckoutConfig(
                it.application,
                Config.PAYPAL_CLIENT_ID,
                Environment.SANDBOX,
                "com.example.shopjava://paypalpay",
                CurrencyCode.USD,
                UserAction.PAY_NOW
            )
        }
        payPalButton.setup(
            createOrder =
            CreateOrder { createOrderActions ->
                val order =
                    Order(
                        intent = OrderIntent.CAPTURE,
                        appContext = AppContext(userAction = UserAction.PAY_NOW),
                        purchaseUnitList =
                        listOf(
                            PurchaseUnit(
                                amount =
                                Amount(currencyCode = CurrencyCode.USD, value = String.format(
                                    Locale.ENGLISH,
                                    "%.2f",
                                    totalPrice / 20.18
                                ))
                            )
                        )
                    )
                createOrderActions.create(order)
            },
            onApprove =
            OnApprove { approval ->
                approval.orderActions.capture {
                    orderRequest.paymentMethod = "PayPal"
                    orderViewModel!!.createOrder(token, orderRequest)
                    bottomSheetDialog.cancel()
                }
            }
        )

        if (checkoutConfig != null) {
            PayPalCheckout.setConfig(checkoutConfig)
        }
    }

    fun setOrderViewModel(orderViewModel: OrderViewModel?) {
        this.orderViewModel = orderViewModel
    }

    fun setToken(token: String?) {
        this.token = token
    }

    companion object {
        private val INSTANCE: PaymentService? = null
        val instance: PaymentService?
            get() = INSTANCE ?: PaymentService()
    }
}