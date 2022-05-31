package com.pourkazemi.mahdi.kalastore.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pourkazemi.mahdi.kalastore.R
import com.pourkazemi.mahdi.kalastore.databinding.FragmentSearchBinding
import com.pourkazemi.mahdi.maktab_hw_18_1.util.viewBinding


class SearchFragment : Fragment(R.layout.fragment_search) {
private val binding:FragmentSearchBinding by viewBinding(FragmentSearchBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}