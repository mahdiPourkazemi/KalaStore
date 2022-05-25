package com.pourkazemi.mahdi.kalastore.data.remote

import com.pourkazemi.mahdi.kalastore.data.model.Kala
import com.pourkazemi.mahdi.kalastore.data.model.KalaCategory
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getListKala(
        orderType: String
    ): Response<List<Kala>>

    suspend fun getListKalaCategory(): Response<List<KalaCategory>>

    suspend fun getSpecialCategoryListKala(
        category: String
    ): Response<List<Kala>>

}