package com.pourkazemi.mahdi.kalastore.ui.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.pourkazemi.mahdi.kalastore.R
import com.pourkazemi.mahdi.kalastore.databinding.FragmentAccountBinding
import com.pourkazemi.mahdi.kalastore.databinding.FragmentHomeBinding
import com.pourkazemi.mahdi.kalastore.ui.adapters.ItemListAdapter
import com.pourkazemi.mahdi.kalastore.ui.viewmodels.ShearedViewModel
import com.pourkazemi.mahdi.maktab_hw_18_1.util.ResultWrapper
import com.pourkazemi.mahdi.maktab_hw_18_1.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by viewBinding(
        FragmentHomeBinding::bind
    )
    private val homeViewModel: ShearedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val popularAdapter = ItemListAdapter {
            Timber.tag("mahdiTest").d("item ${it.name} clicked")
        }
        val dateAdapter = ItemListAdapter {
            Timber.tag("mahdiTest").d("item ${it.name} clicked")
        }
        val rateAdapter = ItemListAdapter {
            Timber.tag("mahdiTest").d("item ${it.name} clicked")
        }
        binding.apply {
            popularRecyclerView.adapter = popularAdapter
            dateRecyclerView.adapter = dateAdapter
            rateRecyclerView.adapter = rateAdapter
        }
        homeViewModel.listOfDateKala.collectIt(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Loading -> {

                    Timber.tag("mahdiTest").d("error")
                }
                is ResultWrapper.Success -> {
                    popularAdapter.submitList(it.value)
                }
                is ResultWrapper.Error -> {

                    Timber.tag("mahdiTest").d("error")
                }
            }
        }
        homeViewModel.listOfPopularKala.collectIt(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Loading -> {

                    Timber.tag("mahdiTest").d("error")
                }
                is ResultWrapper.Success -> {
                    dateAdapter.submitList(it.value)
                }
                is ResultWrapper.Error -> {

                    Timber.tag("mahdiTest").d("error")
                }
            }
        }

        homeViewModel.listOfRatingKala.collectIt(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Loading -> {

                }
                is ResultWrapper.Success -> {
                    rateAdapter.submitList(it.value)
                }
                is ResultWrapper.Error -> {

                }
            }
        }

    }

    fun <T> StateFlow<T>.collectIt(lifecycleOwner: LifecycleOwner, function: (T) -> Unit) {
        lifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                collect {
                    function.invoke(it)
                }
            }
        }
    }
}