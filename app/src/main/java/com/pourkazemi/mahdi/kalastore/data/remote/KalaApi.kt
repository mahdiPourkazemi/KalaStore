package com.pourkazemi.mahdi.kalastore.data.remote

import com.pourkazemi.mahdi.kalastore.App.Companion.KEY
import com.pourkazemi.mahdi.kalastore.App.Companion.SECRET
import com.pourkazemi.mahdi.kalastore.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface KalaApi {

    @GET("products")
    suspend fun getListKala(
        @Query("orderby") orderType: String,
        @Query("consumer_key") key: String = KEY,
        @Query("consumer_secret") secret: String = SECRET
    ): Response<List<Kala>>

    @GET("products/categories")
    suspend fun getListKalaCategory(
        @Query("consumer_key") key: String = KEY,
        @Query("consumer_secret") secret: String = SECRET
    ): Response<List<KalaCategory>>

    @GET("products")
    suspend fun getSpecialCategoryListKala(
        @Query("category") category: String,
        @Query("consumer_key") key: String = KEY,
        @Query("consumer_secret") secret: String = SECRET
    ): Response<List<Kala>>


    @GET("products")
    suspend fun searchListKala(
        @Query("search") search: String,
        @Query("consumer_key") key: String = KEY,
        @Query("consumer_secret") secret: String = SECRET
    ): Response<List<Kala>>

    @GET("products")
    suspend fun getSpecialSellProduct(
        @Query("category") category: String = "119",
        @Query("consumer_key") key: String = KEY,
        @Query("consumer_secret") secret: String = SECRET
    ): Response<List<Kala>>

    @POST("customers")
    suspend fun createCustomer(
        @Body customer: Customer,
        @Query("consumer_key") key: String = KEY,
        @Query("consumer_secret") secret: String = SECRET,
    ): Response<Customer>

    @POST("orders")
    suspend fun createOrder(
        @Query("customer_id") customerId: Int,
        @Body order: Order,
        @Query("consumer_key") key: String = KEY,
        @Query("consumer_secret") secret: String = SECRET,
    ): Response<List<Order>>

    @GET("orders")
    suspend fun getOrderList(
        @Query("status") status: String,
        @Query("customer") customerId: Int,
        @Query("consumer_key") key: String = KEY,
        @Query("consumer_secret") secret: String = SECRET,
        ): Response<List<ReceiveOrder>>

    @GET("products/{id}")
    suspend fun getSpecialProduct(
        @Path("id") id: String,
        @Query("consumer_key") key: String = KEY,
        @Query("consumer_secret") secret: String = SECRET,
    ): Response<Kala>

    @GET("products/reviews")
    suspend fun getListOfReview(
        @Query("product") id: Int,
        @Query("consumer_key") key: String = KEY,
        @Query("consumer_secret") secret: String = SECRET,
    ): Response<List<Review>>

    @POST("products/reviews")
    suspend fun createReview(
        @Body review:Review,
        @Query("consumer_key") key: String = KEY,
        @Query("consumer_secret") secret: String = SECRET,
    ): Response<Review>

    @GET("products")
    suspend fun newProductList(
       @Query("after") date:String,
        @Query("consumer_key") key: String = KEY,
        @Query("consumer_secret") secret: String = SECRET
    ): Response<List<Kala>>
}