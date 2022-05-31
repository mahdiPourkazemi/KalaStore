package com.pourkazemi.mahdi.kalastore.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pourkazemi.mahdi.kalastore.R
import com.pourkazemi.mahdi.kalastore.databinding.FragmentAccountBinding
import com.pourkazemi.mahdi.kalastore.databinding.FragmentCartBinding
import com.pourkazemi.mahdi.maktab_hw_18_1.util.viewBinding

class CartFragment : Fragment(R.layout.fragment_cart) {

    private val binding: FragmentCartBinding by viewBinding(
        FragmentCartBinding ::bind
    )
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}