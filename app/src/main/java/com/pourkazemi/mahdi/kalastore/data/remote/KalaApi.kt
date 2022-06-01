package com.pourkazemi.mahdi.kalastore.data.remote

import com.pourkazemi.mahdi.kalastore.App.Companion.KEY
import com.pourkazemi.mahdi.kalastore.App.Companion.SECRET
import com.pourkazemi.mahdi.kalastore.data.model.Customer
import com.pourkazemi.mahdi.kalastore.data.model.Kala
import com.pourkazemi.mahdi.kalastore.data.model.KalaCategory
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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
        @Body customer: Customer
    ):Response<Customer>

}