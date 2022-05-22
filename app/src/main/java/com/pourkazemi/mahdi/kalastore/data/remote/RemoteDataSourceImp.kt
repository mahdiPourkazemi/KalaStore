package com.pourkazemi.mahdi.kalastore.data.remote

import com.pourkazemi.mahdi.kalastore.data.model.Kala
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImp @Inject constructor(
    private val kalaApi: KalaApi
) : RemoteDataSource {

    override suspend fun getListKala(
        orderType: String,
        key: String,
        secret: String
    ): Response<List<Kala>> {
        return kalaApi.getListKala(orderType, key, secret)
    }
}