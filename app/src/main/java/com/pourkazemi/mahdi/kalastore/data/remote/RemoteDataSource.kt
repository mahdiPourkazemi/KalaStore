package com.pourkazemi.mahdi.kalastore.data.remote

import com.pourkazemi.mahdi.kalastore.data.model.Kala
import retrofit2.Response
import retrofit2.http.Query

interface RemoteDataSource {
    suspend fun getListKala(
        orderType: String
    ): Response<List<Kala>>
}