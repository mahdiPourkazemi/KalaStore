package com.pourkazemi.mahdi.kalastore.ui.home

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
import com.pourkazemi.mahdi.kalastore.databinding.HomeModelItemBinding
import com.pourkazemi.mahdi.kalastore.ui.detail.DetailFragmentDirections
import timber.log.Timber

class ItemListAdapter :
    androidx.recyclerview.widget.ListAdapter<Kala, ItemListAdapter.ItemViewHolder>(ItemDiffUtil()) {


    inner class ItemViewHolder(
        private val binding: HomeModelItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val action= DetailFragmentDirections.toDetailFragment( getItem(bindingAdapterPosition) )
                itemView.findNavController().navigate(action)
            }
        }

        fun mBind(kala: Kala) {

            Timber.tag("mahdiTest").d("bind")
            binding.homeTv.text = kala.name
            kala.image.let { listOfImage->
                val imgUri = listOfImage[0].toUri().buildUpon().build()
                Glide.with(binding.root)
                    .load(imgUri)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.ic_broken_image)
                    )
                    .into(binding.homeImageView)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ItemViewHolder(
        HomeModelItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: ItemViewHolder,
        position: Int
    ) {
        holder.mBind(getItem(position))
    }
}

class ItemDiffUtil : DiffUtil.ItemCallback<Kala>() {

    override fun areItemsTheSame(
        oldItem: Kala,
        newItem: Kala
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Kala,
        newItem: Kala
    ): Boolean {
        return (oldItem == newItem)
    }
}