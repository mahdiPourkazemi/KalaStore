package com.pourkazemi.mahdi.kalastore.data

import com.pourkazemi.mahdi.kalastore.data.local.LocalDataSource
import com.pourkazemi.mahdi.kalastore.data.model.Customer
import com.pourkazemi.mahdi.kalastore.data.model.Kala
import com.pourkazemi.mahdi.kalastore.data.model.Order
import com.pourkazemi.mahdi.kalastore.data.model.Review
import com.pourkazemi.mahdi.kalastore.data.remote.RemoteDataSource
import com.pourkazemi.mahdi.kalastore.di.DispatchersModule
import com.pourkazemi.mahdi.maktab_hw_18_1.util.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
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

    suspend fun insertCustomer(customer: Customer) {
        localDataSourceImp.insertCustomer(customer)
    }

    suspend fun deleteCustomer(customer: Customer) {
        localDataSourceImp.deleteCustomer(customer)
    }

    fun getAllCustomer(): Flow<List<Customer>> {
        return localDataSourceImp.getAllCustomer()
    }

    suspend fun insertKala(kala: Kala) {
        localDataSourceImp.insertKala(kala)
    }

    suspend fun deleteKala(kala: Kala) {
        localDataSourceImp.deleteKala(kala)
    }

    fun getAllKala(): Flow<List<Kala>> {
        return localDataSourceImp.getAllKala()
    }
    suspend fun createOrder(order: Order){
       remoteDataSourceImp.createOrder(order)
    }

    suspend fun getListOfReview(product_id: Int)= safeApiCall(dispatcher){
        remoteDataSourceImp.getListOfReview(product_id)
    }

    suspend fun createReview(review: Review)= safeApiCall(dispatcher){
        remoteDataSourceImp.createReview(review)
    }
}