package com.pourkazemi.mahdi.kalastore.data

import com.pourkazemi.mahdi.kalastore.data.model.Kala
import com.pourkazemi.mahdi.kalastore.data.remote.RemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSourceImp: RemoteDataSource,
) {
    suspend fun getListKala(
        orderType: String,
        key: String,
        secret: String
    ): Response<List<Kala>> {
        return remoteDataSourceImp.getListKala(
            orderType,
            key,
            secret
        )
    }
}