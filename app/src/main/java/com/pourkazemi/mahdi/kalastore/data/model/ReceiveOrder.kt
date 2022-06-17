package com.pourkazemi.mahdi.kalastore.data.model

data class ReceiveOrder(
    val id: Int,
    val status: String,
    val line_items:List<Product>
)
