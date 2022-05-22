package com.pourkazemi.mahdi.kalastore.data.remote

import com.pourkazemi.mahdi.kalastore.data.model.Kala
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KalaApi {

    @GET("products")
    suspend fun getListKala(
        @Query("orderby") orderType: String,
        @Query("consumer_key") key: String = "ck_c91e8d092377dc1b04dffcd3244791fa465c008e",
        @Query("consumer_secret") secret: String = "cs_0697d4f8163631488d96cf635e9e10e41ddc15d6"
    ): Response<List<Kala>>
}