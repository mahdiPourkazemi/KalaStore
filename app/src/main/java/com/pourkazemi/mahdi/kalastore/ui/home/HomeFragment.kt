package com.pourkazemi.mahdi.kalastore.ui.home

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.pourkazemi.mahdi.kalastore.R
import com.pourkazemi.mahdi.kalastore.databinding.FragmentHomeBinding
import com.pourkazemi.mahdi.maktab_hw_18_1.util.ResultWrapper
import com.pourkazemi.mahdi.maktab_hw_18_1.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by viewBinding(
        FragmentHomeBinding::bind
    )
    private val homeViewModel: ShearedViewModel by activityViewModels()

    private val sliderAdapter = Slider()
    private val popularAdapter = ItemListAdapter()
    private val dateAdapter = ItemListAdapter()
    private val rateAdapter = ItemListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            popularRecyclerView.adapter = popularAdapter
            dateRecyclerView.adapter = dateAdapter
            rateRecyclerView.adapter = rateAdapter
            viewPager.adapter = sliderAdapter
            TabLayoutMediator(dots, viewPager) { tab, position ->
                tab.text = position.toString()
            }.attach()

            errorSlider.setOnClickListener {
                homeViewModel.getSpecialSell()
            }
            errorPopular.setOnClickListener {
               homeViewModel.getKalaList("popularity")
            }
            errorDate.setOnClickListener {
               homeViewModel.getKalaList("date")
            }
            errorRate.setOnClickListener {
                homeViewModel.getKalaList("rating")
            }
/*            refreshLayout.setOnRefreshListener {
                homeViewModel.getListProduct()
                refreshLayout.isRefreshing = false
            }*/
        }
        sliderInit()
        popularListInit()
        dateListInit()
        rateListInit()

    }

    private fun rateListInit() {
        homeViewModel.listOfRatingKala.collectIt(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Loading -> {
                    binding.shimmerRvRate.startShimmer()
                }
                is ResultWrapper.Success -> {
                    binding.apply {
                        success(rateRecyclerView, shimmerRvRate, errorRate)
                    }
                    rateAdapter.submitList(it.value)
                }
                is ResultWrapper.Error -> {
                    binding.apply {
                        error(rateRecyclerView, shimmerRvRate, errorRate)
                    }
                }
            }
        }
    }

    private fun dateListInit() {
        homeViewModel.listOfDateKala.collectIt(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Loading -> {
                    binding.shimmerRvDate.startShimmer()
                }
                is ResultWrapper.Success -> {
                    binding.apply {
                        success(dateRecyclerView, shimmerRvDate, errorDate)
                    }
                    popularAdapter.submitList(it.value)
                }
                is ResultWrapper.Error -> {
                    binding.apply {
                        error(dateRecyclerView, shimmerRvDate, errorDate)
                    }
                }
            }
        }
    }

    private fun popularListInit() {
        homeViewModel.listOfPopularKala.collectIt(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Loading -> {
                    binding.shimmerRvPopular.startShimmer()
                }
                is ResultWrapper.Success -> {
                    binding.apply {
                        success(popularRecyclerView, shimmerRvPopular, errorPopular)
                    }
                    dateAdapter.submitList(it.value)
                }
                is ResultWrapper.Error -> {
                    binding.apply {
                        error(popularRecyclerView, shimmerRvPopular, errorPopular)
                    }
                }
            }
        }
    }

    private fun sliderInit() {
        homeViewModel.listOfSpecialSell.collectIt(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Loading -> {
                    binding.shimmerSlider.startShimmer()
                }
                is ResultWrapper.Success -> {
                    binding.shimmerSlider.stopShimmer()
                    binding.dots.visibility = View.VISIBLE
                    binding.shimmerSlider.visibility = View.GONE
                    binding.viewPager.visibility = View.VISIBLE
                    binding.errorSlider.visibility = View.GONE
                    sliderAdapter.submitList(it.value[0].image)
                }
                is ResultWrapper.Error -> {
                    binding.shimmerSlider.stopShimmer()
                    binding.shimmerSlider.visibility = View.GONE
                    binding.errorSlider.visibility = View.VISIBLE
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
        errorButton: Button
    ) {
        shimmerFrameLayout.stopShimmer()
        recycleView.visibility = View.VISIBLE
        shimmerFrameLayout.visibility = View.GONE
        errorButton.visibility = View.GONE
    }

    private fun error(
        recycleView: RecyclerView,
        shimmerFrameLayout: ShimmerFrameLayout,
        errorButton: Button
    ) {
        shimmerFrameLayout.stopShimmer()
        recycleView.visibility = View.GONE
        shimmerFrameLayout.visibility = View.VISIBLE
        errorButton.visibility = View.VISIBLE
    }
}