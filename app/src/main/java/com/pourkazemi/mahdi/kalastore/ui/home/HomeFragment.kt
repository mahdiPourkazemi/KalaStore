package com.pourkazemi.mahdi.kalastore.ui.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.view.get
import androidx.core.view.marginEnd
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
import timber.log.Timber


@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val binding: FragmentHomeBinding by viewBinding(
        FragmentHomeBinding::bind
    )
    private val homeViewModel: ShearedViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val popularAdapter = ItemListAdapter()
        val dateAdapter = ItemListAdapter()
        val rateAdapter = ItemListAdapter()
        val sliderAdapter = Slider()

        binding.apply {
            popularRecyclerView.adapter = popularAdapter
            dateRecyclerView.adapter = dateAdapter
            rateRecyclerView.adapter = rateAdapter
            viewPager.adapter = sliderAdapter
            TabLayoutMediator(dots, viewPager) { tab, position ->
                tab.text = position.toString()
            }.attach()

        }
        homeViewModel.listOfSpecialSell.collectIt(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Loading -> {
                    binding.shimmerSlider.startShimmer()
                    Timber.tag("mahdiTest").d("error")
                }
                is ResultWrapper.Success -> {
                   binding.shimmerSlider.stopShimmer()
                    binding.dots.visibility=View.VISIBLE
                    binding.shimmerSlider.visibility=View.GONE
                    binding.viewPager.visibility=View.VISIBLE
                    sliderAdapter.submitList(it.value[0].image)
                }
                is ResultWrapper.Error -> {
                    binding.shimmerSlider.stopShimmer()
                    binding.shimmerSlider.visibility=View.GONE
                    Timber.tag("mahdiTest").d("error")
                }
            }
        }

        homeViewModel.listOfDateKala.collectIt(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Loading -> {
                    binding.shimmerRvDate.startShimmer()
                    Timber.tag("mahdiTest").d("error")
                }
                is ResultWrapper.Success -> {
                    binding.apply {
                        success(dateRecyclerView, dateTv, shimmerRvDate, shimmerTvDate)
                    }
                    popularAdapter.submitList(it.value)
                }
                is ResultWrapper.Error -> {
                    binding.apply {
                        error(dateRecyclerView, dateTv, shimmerRvDate, shimmerTvDate)
                    }
                    Timber.tag("mahdiTest").d("error")
                }
            }
        }
        homeViewModel.listOfPopularKala.collectIt(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Loading -> {
                    binding.shimmerRvPopular.startShimmer()
                    Timber.tag("mahdiTest").d("error")
                }
                is ResultWrapper.Success -> {
                    binding.apply {
                        success(popularRecyclerView, popularTv, shimmerRvPopular, shimmerTvPopular)
                    }
                    dateAdapter.submitList(it.value)
                }
                is ResultWrapper.Error -> {
                    binding.apply {
                        error(popularRecyclerView, popularTv, shimmerRvPopular, shimmerTvPopular)
                    }
                    Timber.tag("mahdiTest").d("error")
                }
            }
        }

        homeViewModel.listOfRatingKala.collectIt(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Loading -> {
                    binding.shimmerRvRate.startShimmer()
                }
                is ResultWrapper.Success -> {
                    binding.apply {
                        success(rateRecyclerView, rateTv, shimmerRvRate, shimmerTvRate)
                    }
                    rateAdapter.submitList(it.value)
                }
                is ResultWrapper.Error -> {
                    binding.apply {
                        error(rateRecyclerView, rateTv, shimmerRvRate, shimmerTvRate)
                    }
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
        textView: TextView,
        shimmerFrameLayout: ShimmerFrameLayout,
        shimmerTextView: TextView
    ) {
        shimmerFrameLayout.stopShimmer()
        recycleView.visibility = View.VISIBLE
        textView.visibility = View.VISIBLE
        shimmerFrameLayout.visibility = View.GONE
        shimmerTextView.visibility = View.GONE
    }

    private fun error(
        recycleView: RecyclerView,
        textView: TextView,
        shimmerFrameLayout: ShimmerFrameLayout,
        shimmerTextView: TextView
    ) {

        shimmerFrameLayout.stopShimmer()
        recycleView.visibility = View.GONE
        textView.visibility = View.GONE
        shimmerFrameLayout.visibility = View.GONE
        shimmerTextView.visibility = View.GONE
    }
}