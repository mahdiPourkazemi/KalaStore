package com.pourkazemi.mahdi.kalastore.ui.internet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.pourkazemi.mahdi.kalastore.R
import com.pourkazemi.mahdi.kalastore.databinding.FragmentNoInternetBinding
import com.pourkazemi.mahdi.kalastore.databinding.FragmentSearchBinding
import com.pourkazemi.mahdi.maktab_hw_18_1.util.viewBinding


class NoInternetFragment : Fragment(R.layout.fragment_no_internet) {

    private val binding: FragmentNoInternetBinding by viewBinding(FragmentNoInternetBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loadingAnime.playAnimation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /* activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.onBackPressed()
            }
        })*/
       // findNavController().
    }

}