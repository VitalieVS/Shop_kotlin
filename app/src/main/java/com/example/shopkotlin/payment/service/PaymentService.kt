package com.example.shopkotlin.payment.service

import android.content.Context
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.shopkotlin.login.service.UserService
import com.example.shopkotlin.order.request.OrderRequest
import com.example.shopkotlin.order.ui.OrderViewModel
import com.example.shopkotlin.payment.paypal.Config
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.approve.OnApprove
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.config.SettingsConfig
import com.paypal.checkout.createorder.*
import com.paypal.checkout.error.OnError
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
    lateinit var fragmentActivity: FragmentActivity
    private var orderViewModel: OrderViewModel? = null
    private var token: String? = null

    fun setContext(context: Context?) {
        this.context = context
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

                println("first")
                orderRequest.cashBackApplied = userService.cashback
            } else {
                println("second")
                val orderCashBackMax = totalPrice * 0.30 + totalPrice
                orderRequest.cashBackApplied = (orderCashBackMax - totalPrice).toFloat()
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
        val config = CheckoutConfig(
            application = fragmentActivity.application,
            clientId = Config.PAYPAL_CLIENT_ID,
            environment = Environment.SANDBOX,
            returnUrl = "com.example.shopkotlin://paypalpay",
            currencyCode = CurrencyCode.USD,
            userAction = UserAction.PAY_NOW,
            settingsConfig = SettingsConfig(
            loggingEnabled = true
        ))

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
                                )
                                )
                            )
                        ),
                        processingInstruction = ProcessingInstruction.ORDER_COMPLETE_ON_PAYMENT_APPROVAL
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
            },
            onError = OnError { errorInfo ->
                println("error $errorInfo")
            }
        )

        PayPalCheckout.setConfig(config)
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