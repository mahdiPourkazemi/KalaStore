package com.pourkazemi.mahdi.kalastore.ui.detail

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.net.toUri
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.pourkazemi.mahdi.kalastore.R
import com.pourkazemi.mahdi.kalastore.data.model.*
import com.pourkazemi.mahdi.kalastore.databinding.FragmentDetailBinding
import com.pourkazemi.mahdi.maktab_hw_18_1.util.ResultWrapper
import com.pourkazemi.mahdi.maktab_hw_18_1.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding: FragmentDetailBinding by viewBinding(
        FragmentDetailBinding::bind
    )

    private val detailViewModel: DetailViewModel by activityViewModels()
    private val detailNavArgs: DetailFragmentArgs by navArgs()
    val adapter = ReviewItemListAdapter()

    var customer: List<Customer> = listOf()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel.getListOfReview(detailNavArgs.kala.id)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                detailViewModel.flowListCustomer.collectIt {
                    customer = it
                }
            }
        }
        binding.buyButton.setOnClickListener {
            if (customer.isEmpty() == true) {
                Snackbar.make(
                    requireView(),
                    resources.getString(R.string.buyCondition),
                    Snackbar.LENGTH_LONG
                ).show()
            } else {
                customer.let {
                    detailViewModel.createOrder(
                        it[0].id, Order(
                            listOf(
                                Product(
                                    detailNavArgs.kala.id, 1
                                )
                            )
                        )
                    )
                }
            }

        }
        binding.sendReview.setOnClickListener {
            detailViewModel.sendReview(
                Review(
                    0,
                    detailNavArgs.kala.id,
                    binding.reviewEdit.editText?.text.toString(),
                    "123456",//must get form data base but im so sick
                    "mahdi3333@poori75.com",
                    binding.ratingBarSend.rating.toInt(),
                    "null"
                )
            )
        }

        binding.apply {
            description.text = HtmlCompat.fromHtml(
                detailNavArgs.kala.description, HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            priceTv.text = detailNavArgs.kala.price
            name.text = detailNavArgs.kala.name
            detailNavArgs.kala.image.let { listOfImage ->
                val imgUri = listOfImage[0].toUri().buildUpon().build()
                Glide.with(binding.root)
                    .load(imgUri)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.ic_broken_image)
                    )
                    .into(detailImageView)
            }

            reviewList.adapter = adapter
        }
        detailViewModel.ReviewOfProduct.collectIt {
            when (it) {
                is ResultWrapper.Loading -> {
                }
                is ResultWrapper.Success -> {
                    adapter.submitList(it.value)
                }
                is ResultWrapper.Error -> {
                }
            }
        }


    }

    private fun cleanDescription(input: String): String {
        return input.replace("</ br>", "")
            .replace("<p/>", "")
            .replace("</p>", "")
            .replace("<p>", "")
            .replace("<br />", "")
    }

    private fun <T> StateFlow<T>.collectIt(function: (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                collect {
                    function.invoke(it)
                }
            }
        }
    }
}