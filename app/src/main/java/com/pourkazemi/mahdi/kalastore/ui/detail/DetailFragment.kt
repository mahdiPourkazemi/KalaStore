package com.pourkazemi.mahdi.kalastore.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pourkazemi.mahdi.kalastore.R
import com.pourkazemi.mahdi.kalastore.data.model.Kala
import com.pourkazemi.mahdi.kalastore.data.model.Order
import com.pourkazemi.mahdi.kalastore.data.model.Product
import com.pourkazemi.mahdi.kalastore.data.model.Review
import com.pourkazemi.mahdi.kalastore.databinding.FragmentDetailBinding
import com.pourkazemi.mahdi.maktab_hw_18_1.util.ResultWrapper
import com.pourkazemi.mahdi.maktab_hw_18_1.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding: FragmentDetailBinding by viewBinding(
        FragmentDetailBinding::bind
    )

    private val detailViewModel: DetailViewModel by activityViewModels()
    private val detailNavArgs: DetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ReviewItemListAdapter()

        detailViewModel.getListOfReview(detailNavArgs.kala.id)

        binding.buyButton.setOnClickListener {
            detailViewModel.insertKala(
                Kala(
                    detailNavArgs.kala.id,
                    detailNavArgs.kala.name,
                    detailNavArgs.kala.price,
                    detailNavArgs.kala.description,
                    detailNavArgs.kala.image
                )
            )
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

            description.text = cleanDescription(detailNavArgs.kala.description)
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
        detailViewModel.ReviewOfProduct.collectIt(viewLifecycleOwner) {
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

    private fun <T> StateFlow<T>.collectIt(lifecycleOwner: LifecycleOwner, function: (T) -> Unit) {
        lifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                collect {
                    function.invoke(it)
                }
            }
        }
    }
}