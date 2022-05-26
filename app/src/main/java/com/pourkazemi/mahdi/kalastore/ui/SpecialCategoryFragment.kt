package com.pourkazemi.mahdi.kalastore.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil.bind
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.pourkazemi.mahdi.kalastore.R
import com.pourkazemi.mahdi.kalastore.databinding.FragmentSpecialCategoryBinding
import com.pourkazemi.mahdi.kalastore.ui.adapters.SpecialKalaAdapter
import com.pourkazemi.mahdi.kalastore.ui.viewmodels.CategoryViewModel
import com.pourkazemi.mahdi.maktab_hw_18_1.util.ResultWrapper
import com.pourkazemi.mahdi.maktab_hw_18_1.util.viewBinding
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber


class SpecialCategoryFragment : Fragment(R.layout.fragment_special_category) {
    private val binding: FragmentSpecialCategoryBinding by viewBinding(
        FragmentSpecialCategoryBinding::bind
    )

    private val categoryViewModel: CategoryViewModel by activityViewModels()
    private val specialNavArgs: SpecialCategoryFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryViewModel.getSpecialListKala(specialNavArgs.categoryId)

        val specialAdapter = SpecialKalaAdapter {
            //categoryViewModel.getSpecialListKala(it.id.toString())
            val action=DetailFragmentDirections.toDetailFragment(it)
            findNavController().navigate(action)
            Timber.tag("mahdiTest").d("item ${it.name} clicked")
        }
        binding.apply {
            specialRv.adapter = specialAdapter
        }

        categoryViewModel.categorySpecialKalaList.collectIt(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Loading -> {
                    binding.shimmerSpecialRv.startShimmer()
                    Timber.tag("mahdiTest").d("loadingSpecialError")
                }
                is ResultWrapper.Success -> {
                    binding.apply {
                        success(specialRv, shimmerSpecialRv)
                    }
                    specialAdapter.submitList(it.value)
                }
                is ResultWrapper.Error -> {
                    binding.apply {
                        error(specialRv, shimmerSpecialRv)
                    }
                    Timber.tag("mahdiTest").d("specialError")
                }
            }

        }
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

    private fun success(
        recycleView: RecyclerView,
        shimmerFrameLayout: ShimmerFrameLayout,
    ) {
        shimmerFrameLayout.stopShimmer()
        recycleView.visibility = View.VISIBLE
        shimmerFrameLayout.visibility = View.GONE
    }

    private fun error(
        recycleView: RecyclerView,
        shimmerFrameLayout: ShimmerFrameLayout,
    ) {

        shimmerFrameLayout.stopShimmer()
        recycleView.visibility = View.GONE
        shimmerFrameLayout.visibility = View.GONE
    }

}