package com.pourkazemi.mahdi.kalastore.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pourkazemi.mahdi.kalastore.R
import com.pourkazemi.mahdi.kalastore.data.model.Kala
import com.pourkazemi.mahdi.kalastore.data.model.KalaCategory
import com.pourkazemi.mahdi.kalastore.databinding.CategoryModelItemBinding
import com.pourkazemi.mahdi.kalastore.ui.navigation.CategoryFragmentDirections
import timber.log.Timber

class CategoryItemListAdapter :
    androidx.recyclerview.widget.ListAdapter<KalaCategory, CategoryItemListAdapter.CategoryItemViewHolder>(
        CategoryItemDiffUtil()
    ) {


    inner class CategoryItemViewHolder(
        private val binding: CategoryModelItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val action = CategoryFragmentDirections.toSpecialCategoryFragment(
                    getItem(bindingAdapterPosition).id.toString()
                )
                itemView.findNavController().navigate(action)
            }
        }

        fun mBind(kalaCategory: KalaCategory) {

            Timber.tag("mahdiTest").d("bind")
            binding.categoryTextView.text = kalaCategory.name
            kalaCategory.image.let { listOfImage ->
                val imgUri = listOfImage.toUri().buildUpon().build()
                Glide.with(binding.root)
                    .load(imgUri)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.ic_broken_image)
                    )
                    .into(binding.categoryImageView)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = CategoryItemViewHolder(
        CategoryModelItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: CategoryItemViewHolder,
        position: Int
    ) {
        holder.mBind(getItem(position))
    }
}

class CategoryItemDiffUtil : DiffUtil.ItemCallback<KalaCategory>() {

    override fun areItemsTheSame(
        oldItem: KalaCategory,
        newItem: KalaCategory
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: KalaCategory,
        newItem: KalaCategory
    ): Boolean {
        return (oldItem == newItem)
    }
}