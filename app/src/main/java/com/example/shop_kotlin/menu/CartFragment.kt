package com.example.shop_kotlin.menu

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shop_kotlin.R
import com.example.shop_kotlin.cart.container.EmptyCartFragment
import com.example.shop_kotlin.cart.implementation.adapter.ProductPromotionList
import com.example.shop_kotlin.cart.service.CartService
import com.example.shop_kotlin.cart.viewmodel.CartViewModel
import com.example.shop_kotlin.databinding.BottomSheetOrderBinding
import com.example.shop_kotlin.databinding.FragmentCartBinding
import com.example.shop_kotlin.login.service.UserService
import com.example.shop_kotlin.models.Product
import com.example.shop_kotlin.models.State
import com.example.shop_kotlin.order.request.OrderRequest
import com.example.shop_kotlin.order.ui.OrderViewModel
import com.example.shop_kotlin.payment.service.PaymentService
import com.example.shop_kotlin.promotion.model.Promotion
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.*

class CartFragment : Fragment() {
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var bindingSheet: BottomSheetOrderBinding
    private var userService: UserService? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentCartBinding: FragmentCartBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_cart, container, false
        )
        val cartService: CartService? = CartService.instance
        userService = UserService.instance
        cartService?.setContext(Objects.requireNonNull<ViewGroup?>(container).context)
        fragmentCartBinding.cartService = cartService
        return fragmentCartBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val settings: SharedPreferences =
            requireContext().getSharedPreferences(Context.ACCOUNT_SERVICE, 0)
        val token = "Bearer_" + settings.getString("token", "")
        val bottomNavigationView: BottomNavigationView =
            requireActivity().findViewById(R.id.bottom_navigation)
        val orderViewModel: OrderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]
        bottomSheetDialog = BottomSheetDialog(requireActivity(), R.style.BottomSheetDialogTheme)
        userService?.setContext(requireContext())
        val cartService: CartService? = CartService.instance
        cartService?.setFragmentActivity(requireActivity())
        cartService?.setContext(requireContext())
        val paymentService: PaymentService? = PaymentService.instance
        paymentService?.setContext(requireContext())
        paymentService?.setFragmentActivity(requireActivity())
        val recyclerView: RecyclerView = requireView().findViewById(R.id.cartItemsRecyclerView)
        val adapter = ProductPromotionList()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val imageView = requireView().findViewById<ImageView>(R.id.exitCartButton)
        imageView.setOnClickListener {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            bottomNavigationView.visibility = View.VISIBLE
            bottomNavigationView.selectedItemId = R.id.home
        }
        cartService?.let { showFragmentEmptyCartFragment(it.getProductList(), cartService.getPromotionList()) }
        CartViewModel.productMutableLiveData.observe(viewLifecycleOwner, adapter::setProductDataSet)
        CartViewModel.promotionMutableLiveData.observe(
            viewLifecycleOwner,
            adapter::setPromotionDataSet
        )
        CartViewModel.stateMutableLiveData.observe(viewLifecycleOwner) { state ->
            if (state.equals(State.EMPTY_CART) && isAdded) {
                cartService?.let {
                    showFragmentEmptyCartFragment(
                        it.getProductList(),
                        cartService.getPromotionList()
                    )
                }
                bottomNavigationView.visibility = View.VISIBLE
            } else if (isAdded) {
                bottomNavigationView.visibility = View.GONE
            }
        }
        val cardView: CardView = requireView().findViewById(R.id.order_items)
        cardView.setOnClickListener {
            if (userService!!.isAuthorised) {
                bindingSheet = DataBindingUtil.inflate(
                    LayoutInflater.from(requireActivity()),
                    R.layout.bottom_sheet_order,
                    null,
                    false
                )
                paymentService?.setOrderViewModel(orderViewModel)
                bindingSheet.cartService = cartService
                cartService?.let { it1 -> paymentService?.setTotalPrice(it1.getTotalCartPrice()) }
                paymentService?.setToken(token)
                val orderRequest = cartService?.let { it1 ->
                    OrderRequest(
                        it1.getProductList() as List<Product>,
                        cartService.getPromotionList()
                    )
                }
                bindingSheet.orderRequest = orderRequest
                bindingSheet.paymentService = paymentService
                bindingSheet.backButton.setOnClickListener { bottomSheetDialog.cancel() }
                orderRequest?.let { it1 ->
                    paymentService?.setupPayPal(
                        bindingSheet.payPalButton, bottomSheetDialog,
                        it1
                    )
                }
                bottomSheetDialog!!.setContentView(bindingSheet.mainContainer)
                bottomSheetDialog.show()
            } else {
                Toast.makeText(
                    view.context, "Please authorise first!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        OrderViewModel.ORDER_RESPONSE.observe(viewLifecycleOwner) { response ->
            if (response.created && isAdded) {

                bottomSheetDialog.cancel()
                cartService?.resetCart()
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, SuccessFragment()).commit()
                orderViewModel.resetOrderResponse()
            } else if (isAdded) {
                requireActivity().supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, NoInternetFragment()).commit()
            }
        }
    }

    private fun showFragmentEmptyCartFragment(
        productList: List<Product?>,
        promotionList: List<Promotion>
    ) {
        if (productList.isEmpty() && promotionList.isEmpty()) {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, EmptyCartFragment()).commit()
        }
    }
}