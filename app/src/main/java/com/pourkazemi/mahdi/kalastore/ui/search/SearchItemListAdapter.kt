package com.pourkazemi.mahdi.kalastore.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
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

class SearchItemListAdapter :
    androidx.recyclerview.widget.ListAdapter<Kala, SearchItemListAdapter.SearchItemViewHolder>(
        SearchItemDiffUtil()
    ) {


    inner class SearchItemViewHolder(
        private val binding: SpecialModelItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val action =
                    DetailFragmentDirections.toDetailFragment(getItem(bindingAdapterPosition))
                itemView.findNavController().navigate(action)
            }
        }

        fun mBind(kala: Kala) {

            Timber.tag("mahdiTest").d("bind")
            binding.specialTextView.text = kala.name
            kala.image.let { listOfImage ->
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
    ) = SearchItemViewHolder(
        SpecialModelItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: SearchItemViewHolder,
        position: Int
    ) {
        holder.mBind(getItem(position))
    }

/*    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                return FilterResults().apply {
                    values = if (charSequence?.isEmpty() == true) {
                        currentList
                        Timber.tag("mahdiTest").d("filter")
                    } else {

                        Timber.tag("mahdiTest").d("filter")
                        currentList.filter {
                            it.name.contains(charSequence.toString())
                        }.forEach { currentList.add(it) }
                    }
                }
            }

            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: FilterResults?
            ) {
                submitList(filterResults?.values as List<Kala>)
            }

        }
    }*/
}

class SearchItemDiffUtil : DiffUtil.ItemCallback<Kala>() {

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