package com.pourkazemi.mahdi.kalastore.data.model

import com.google.gson.annotations.Expose

data class Review(
    @Expose(serialize = false, deserialize = true)
    val id: Int,
    val product_id: Int,
    val review: String,
    val reviewer: String,
    val reviewer_email:String,
    val rating: Int,
    @Expose(serialize = false, deserialize = true)
    val reviewer_avatar_urls: String
)