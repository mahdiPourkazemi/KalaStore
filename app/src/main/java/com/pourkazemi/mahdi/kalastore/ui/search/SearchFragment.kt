package com.pourkazemi.mahdi.kalastore.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.pourkazemi.mahdi.kalastore.R
import com.pourkazemi.mahdi.kalastore.databinding.FragmentSearchBinding
import com.pourkazemi.mahdi.maktab_hw_18_1.util.ResultWrapper
import com.pourkazemi.mahdi.maktab_hw_18_1.util.viewBinding
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber


class SearchFragment : Fragment(R.layout.fragment_search) {
    private val binding: FragmentSearchBinding by viewBinding(FragmentSearchBinding::bind)
    private val searchViewModel: SearchViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listAdapter = SearchItemListAdapter()
        binding.searchRecyclerView.adapter = listAdapter

        searchViewModel.listOfSearch.collectIt(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Loading -> {
                    loading()
                }
                is ResultWrapper.Success -> {
                    binding.apply {
                        success(searchRecyclerView, shimmerSpecialRv)
                    }
                    listAdapter.submitList(it.value)
                }
                is ResultWrapper.Error -> {
                    binding.apply {
                        error(searchRecyclerView, shimmerSpecialRv)
                    }
                }
            }
        }
        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return if (query.isNullOrBlank()) false else {
                    searchViewModel.search(query)
                    return true
                }
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return if (newText.isNullOrBlank()) false else {
                    searchViewModel.search(newText)
                    true
                }
            }
        })
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
        shimmerFrameLayout: ShimmerFrameLayout
    ) {
        shimmerFrameLayout.stopShimmer()
        recycleView.visibility = View.VISIBLE
        shimmerFrameLayout.visibility = View.INVISIBLE
    }

    private fun error(
        recycleView: RecyclerView,
        shimmerFrameLayout: ShimmerFrameLayout
    ) {

        shimmerFrameLayout.stopShimmer()
        recycleView.visibility = View.INVISIBLE
        shimmerFrameLayout.visibility = View.INVISIBLE
    }

    private fun loading() {
        binding.apply {
            searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    Timber.tag("mahdiTest").d("focuse")
                    shimmerSpecialRv.visibility = View.VISIBLE
                    shimmerSpecialRv.startShimmer()
                } else {
                    shimmerSpecialRv.visibility = View.INVISIBLE
                    shimmerSpecialRv.stopShimmer()
                }
            }
        }
    }

}