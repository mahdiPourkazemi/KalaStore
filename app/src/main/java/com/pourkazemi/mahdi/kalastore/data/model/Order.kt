package com.pourkazemi.mahdi.kalastore.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

data class Order(
    val line_items: List<Product>
)
