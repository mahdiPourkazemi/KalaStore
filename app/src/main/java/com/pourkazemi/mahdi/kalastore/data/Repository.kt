package com.pourkazemi.mahdi.kalastore.data

import com.pourkazemi.mahdi.kalastore.data.local.LocalDataSource
import com.pourkazemi.mahdi.kalastore.data.model.Customer
import com.pourkazemi.mahdi.kalastore.data.remote.RemoteDataSource
import com.pourkazemi.mahdi.kalastore.di.DispatchersModule
import com.pourkazemi.mahdi.maktab_hw_18_1.util.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    @DispatchersModule.IoDispatcher
    private val dispatcher: CoroutineDispatcher,
    private val remoteDataSourceImp: RemoteDataSource,
    private val localDataSourceImp: LocalDataSource
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

    suspend fun searchListKala(search: String) = safeApiCall(dispatcher) {
        remoteDataSourceImp.searchListKala(search)
    }

    suspend fun getSpecialSellProduct() = safeApiCall(dispatcher) {
        remoteDataSourceImp.getSpecialSellProduct()
    }

    suspend fun createCustomer(customer: Customer) = safeApiCall(dispatcher) {
        remoteDataSourceImp.createCustomer(customer)
    }
    suspend fun insertCustomer(customer: Customer){
       localDataSourceImp.insertCustomer(customer)
    }
    suspend fun deleteCustomer(customer: Customer){
        localDataSourceImp.deleteCustomer(customer)
    }
    fun getAllCustomer():Flow<List<Customer>>{
        return localDataSourceImp.getAllCustomer()
    }
}