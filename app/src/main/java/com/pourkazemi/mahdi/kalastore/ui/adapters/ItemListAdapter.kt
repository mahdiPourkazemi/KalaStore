package com.pourkazemi.mahdi.kalastore.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pourkazemi.mahdi.kalastore.R
import com.pourkazemi.mahdi.kalastore.data.model.Kala
import com.pourkazemi.mahdi.kalastore.databinding.ModelItemBinding
import timber.log.Timber

class ItemListAdapter(private val clickListener: (Kala) -> Unit) :
    androidx.recyclerview.widget.ListAdapter<Kala, ItemListAdapter.ItemViewHolder>(ItemDiffUtil()) {


    inner class ItemViewHolder(
        private val binding: ModelItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                clickListener.invoke(getItem(bindingAdapterPosition))
                Timber.tag("mahdiTest").d("itemView clicked")
                Log.d("mahdiTest","clicked")
            }
        }

        fun mBind(kala: Kala) {

            Timber.tag("mahdiTest").d("bind")
            binding.textView.text = kala.name
            kala.image.let {listOfImage->
                val imgUri = listOfImage[0].toUri().buildUpon().build()
                Glide.with(binding.root)
                    .load(imgUri)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.ic_broken_image)
                    )
                    .into(binding.imageView)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ItemViewHolder(
        ModelItemBinding.inflate(
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
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: Kala,
        newItem: Kala
    ): Boolean {
        return (oldItem.id == newItem.id)
    }
}