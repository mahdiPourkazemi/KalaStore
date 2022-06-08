package com.pourkazemi.mahdi.kalastore.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pourkazemi.mahdi.kalastore.R
import com.pourkazemi.mahdi.kalastore.data.model.Review
import com.pourkazemi.mahdi.kalastore.databinding.ReviewItemItemBinding

class ReviewItemListAdapter :
    androidx.recyclerview.widget.ListAdapter<Review, ReviewItemListAdapter.ReviewItemListHolder>(
        ReviewItemDiffUtil()
    ) {


    inner class ReviewItemListHolder(
        private val binding: ReviewItemItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun mBind(review: Review) {
            binding.review.text = cleanDescription(review.review)
            binding.reviewer.text = review.reviewer
            binding.ratingBar.rating=review.rating.toFloat()
            review.reviewer_avatar_urls.let { image ->
                val imgUri =image.toUri().buildUpon().build()
                Glide.with(binding.root)
                    .load(imgUri)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.ic_broken_image)
                    )
                    .into(binding.avatarReviewImageView)
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ReviewItemListHolder(
        ReviewItemItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(
        holder: ReviewItemListHolder,
        position: Int
    ) {
        holder.mBind(getItem(position))
    }

    private fun cleanDescription(input: String): String {
        return input.replace("</ br>", "")
            .replace("<p/>", "")
            .replace("</p>", "")
            .replace("<p>", "")
            .replace("<br />", "")
    }
}

class ReviewItemDiffUtil : DiffUtil.ItemCallback<Review>() {

    override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
        return (oldItem == newItem)
    }
}