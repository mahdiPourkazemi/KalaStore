package com.pourkazemi.mahdi.kalastore.data.remote

import com.pourkazemi.mahdi.kalastore.App
import com.pourkazemi.mahdi.kalastore.data.model.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Query

interface RemoteDataSource {

    suspend fun getListKala(
        orderType: String
    ): Response<List<Kala>>

    suspend fun getListKalaCategory(): Response<List<KalaCategory>>

    suspend fun getSpecialCategoryListKala(
        category: String
    ): Response<List<Kala>>

    suspend fun searchListKala(
        search: String
    ): Response<List<Kala>>

    suspend fun getSpecialSellProduct(): Response<List<Kala>>

    suspend fun createCustomer(customer: Customer): Response<Customer>

    suspend fun createOrder(order: Order): Any

    suspend fun getListOfReview( product_id: Int ): Response<List<Review>>

    suspend fun createReview( review:Review ): Response<Review>

    suspend fun newProductList( date:String ): Response<List<Kala>>
}