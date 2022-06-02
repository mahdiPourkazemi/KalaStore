package com.pourkazemi.mahdi.kalastore.ui.cart

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pourkazemi.mahdi.kalastore.MainActivity
import com.pourkazemi.mahdi.kalastore.R
import com.pourkazemi.mahdi.kalastore.data.model.Kala
import com.pourkazemi.mahdi.kalastore.data.model.Order
import com.pourkazemi.mahdi.kalastore.data.model.Product
import com.pourkazemi.mahdi.kalastore.databinding.FragmentCartBinding
import com.pourkazemi.mahdi.maktab_hw_18_1.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private val binding: FragmentCartBinding by viewBinding(
        FragmentCartBinding::bind
    )

    private val cardViewModel: CardViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CartItemAdapter()
        binding.cartList.adapter = adapter

        val productList = mutableListOf<Product>()

        cardViewModel.dataBaseKala.collectIt(viewLifecycleOwner) {
            adapter.submitList(it)
            productList.addAll(kalaListToProductList(it))
        }
        binding.sendOrderButton.setOnClickListener {
            cardViewModel.createOrder(
                Order(
                    productList
                )
            )
        }
    }
    private fun kalaListToProductList(kalaList:List<Kala>):List<Product>{
        return kalaList.map { Product(it.id,1) }
    }

    private fun <T> StateFlow<T>.collectIt(lifecycleOwner: LifecycleOwner, function: (T) -> Unit) {
        lifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                collect {
                    function.invoke(it)
                }
            }
        }
    }
}