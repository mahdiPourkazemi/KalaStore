package com.pourkazemi.mahdi.kalastore.ui.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.pourkazemi.mahdi.kalastore.R
import com.pourkazemi.mahdi.kalastore.databinding.FragmentCategoryBinding
import com.pourkazemi.mahdi.kalastore.ui.adapters.CategoryItemListAdapter
import com.pourkazemi.mahdi.kalastore.ui.viewmodels.CategoryViewModel
import com.pourkazemi.mahdi.maktab_hw_18_1.util.ResultWrapper
import com.pourkazemi.mahdi.maktab_hw_18_1.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CategoryFragment : Fragment(R.layout.fragment_category) {
    private val binding: FragmentCategoryBinding by viewBinding(
        FragmentCategoryBinding::bind
    )

    private val categoryViewModel: CategoryViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryAdapter = CategoryItemListAdapter {
            Timber.tag("mahdiTest").d("item ${it.name} clicked")
        }
        binding.apply {
            categoryRv.adapter = categoryAdapter
        }

        categoryViewModel.categoryList.collectIt(viewLifecycleOwner){
            when (it) {
                is ResultWrapper.Loading -> {
                    binding.shimmerCategoryRv.startShimmer()
                    Timber.tag("mahdiTest").d("error")
                }
                is ResultWrapper.Success -> {
                    binding.apply {
                        success(categoryRv,  shimmerCategoryRv)
                    }
                    categoryAdapter.submitList(it.value)
                }
                is ResultWrapper.Error -> {
                    binding.apply {
                        error(categoryRv,  shimmerCategoryRv)
                    }
                    Timber.tag("mahdiTest").d("error")
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