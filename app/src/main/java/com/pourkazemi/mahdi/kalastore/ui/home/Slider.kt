package com.pourkazemi.mahdi.kalastore.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pourkazemi.mahdi.kalastore.R
import com.pourkazemi.mahdi.kalastore.data.model.Kala
import com.pourkazemi.mahdi.kalastore.databinding.SliderItemBinding
import timber.log.Timber

class Slider :
    androidx.recyclerview.widget.ListAdapter<String, Slider.SliderItemViewHolder>(SliderItemDiffUtil()) {


    inner class SliderItemViewHolder(
        private val binding: SliderItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
/*            itemView.setOnClickListener {
                val action= DetailFragmentDirections.toDetailFragment( getItem(bindingAdapterPosition) )
                itemView.findNavController().navigate(action)
            }*/
        }

        fun mBind(string: String) {

            Timber.tag("mahdiTest").d("bind")
            //binding.homeTv.text = kala.name
            string.let { image ->
                val imgUri = image.toUri().buildUpon().build()
                Glide.with(binding.root)
                    .load(imgUri)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.ic_broken_image)
                    )
                    .into(binding.sliderImageView)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = SliderItemViewHolder(
        SliderItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: SliderItemViewHolder,
        position: Int
    ) {
        holder.mBind(getItem(position))
    }
}

class SliderItemDiffUtil : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}