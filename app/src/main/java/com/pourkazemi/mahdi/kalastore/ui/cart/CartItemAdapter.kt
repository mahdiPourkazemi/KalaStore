package com.pourkazemi.mahdi.kalastore.ui.cart

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pourkazemi.mahdi.kalastore.R
import com.pourkazemi.mahdi.kalastore.data.model.Kala
import com.pourkazemi.mahdi.kalastore.databinding.SpecialModelItemBinding
import com.pourkazemi.mahdi.kalastore.ui.detail.DetailFragmentDirections
import timber.log.Timber

class CartItemAdapter :
    androidx.recyclerview.widget.ListAdapter<Kala, CartItemAdapter.CartItemViewHolder>(
        CartItemDiffUtil()
    ) {


    inner class CartItemViewHolder(
        private val binding: SpecialModelItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {

            Log.d("mahdiTest",currentList.groupingBy {it.id}.eachCount().toString())
            Log.d("mahdiTest",currentList.count().toString())
            itemView.setOnClickListener {
                val action =
                    DetailFragmentDirections.toDetailFragment(getItem(bindingAdapterPosition))
                itemView.findNavController().navigate(action)
            }
        }

        fun mBind(kala: Kala) {

            binding.specialTextView.text = kala.name
            kala.image.let { listOfImage->
                val imgUri = listOfImage[0].toUri().buildUpon().build()
                Glide.with(binding.root)
                    .load(imgUri)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.ic_broken_image)
                    )
                    .into(binding.specialImageView)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = CartItemViewHolder(
        SpecialModelItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: CartItemViewHolder,
        position: Int
    ) {
        holder.mBind(getItem(position))
    }
}

class CartItemDiffUtil : DiffUtil.ItemCallback<Kala>() {

    override fun areItemsTheSame(
        oldItem: Kala,
        newItem: Kala
    ): Boolean {
        Log.d("mahdiTest","item same")
        return false
    }

    override fun areContentsTheSame(
        oldItem: Kala,
        newItem: Kala
    ): Boolean {

        Log.d("mahdiTest","item same")
        return false
    }
}