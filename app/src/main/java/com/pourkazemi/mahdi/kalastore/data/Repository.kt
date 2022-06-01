package com.pourkazemi.mahdi.kalastore.data

import com.pourkazemi.mahdi.kalastore.data.model.Kala
import com.pourkazemi.mahdi.kalastore.data.remote.RemoteDataSource
import com.pourkazemi.mahdi.kalastore.di.DispatchersModule
import com.pourkazemi.mahdi.maktab_hw_18_1.util.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    @DispatchersModule.IoDispatcher
    private val dispatcher: CoroutineDispatcher,
    private val remoteDataSourceImp: RemoteDataSource,
) {
    suspend fun getListKala(
        orderType: String
    ) = safeApiCall(dispatcher) {
        remoteDataSourceImp.getListKala(
            orderType
        )
    }

    suspend fun getListKalaCategory() = safeApiCall(dispatcher) {
        remoteDataSourceImp.getListKalaCategory()
    }

    suspend fun getSpecialCategoryListKala(
        category: String
    ) = safeApiCall(dispatcher) {
        remoteDataSourceImp.getSpecialCategoryListKala(category)
    }
    suspend fun searchListKala(search: String)= safeApiCall(dispatcher){
       remoteDataSourceImp.searchListKala(search)
    }

    suspend fun getSpecialSellProduct()= safeApiCall(dispatcher){
        remoteDataSourceImp.getSpecialSellProduct()
    }
}