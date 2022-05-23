package com.pourkazemi.mahdi.kalastore.data.remote

import com.pourkazemi.mahdi.kalastore.App.Companion.KEY
import com.pourkazemi.mahdi.kalastore.App.Companion.SECRET
import com.pourkazemi.mahdi.kalastore.data.model.Kala
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KalaApi {

    @GET("products")
    suspend fun getListKala(
        @Query("orderby") orderType: String,
        @Query("consumer_key") key: String = KEY,
        @Query("consumer_secret") secret: String = SECRET
    ): Response<List<Kala>>
}