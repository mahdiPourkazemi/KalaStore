package com.pourkazemi.mahdi.kalastore.data.remote

import com.pourkazemi.mahdi.kalastore.data.model.Customer
import com.pourkazemi.mahdi.kalastore.data.model.KalaCategory
import com.pourkazemi.mahdi.kalastore.data.model.Kala
import com.pourkazemi.mahdi.kalastore.data.model.Order
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSourceImp @Inject constructor(
    private val kalaApi: KalaApi
) : RemoteDataSource {

    override suspend fun getListKala(
        orderType: String
    ): Response<List<Kala>> {
        return kalaApi.getListKala(orderType)
    }

    override suspend fun getListKalaCategory(): Response<List<KalaCategory>> {
        return kalaApi.getListKalaCategory()
    }

    override suspend fun getSpecialCategoryListKala(
        category: String
    ): Response<List<Kala>> {
        return kalaApi.getSpecialCategoryListKala(category)
    }

    override suspend fun searchListKala(search: String): Response<List<Kala>> {
       return kalaApi.searchListKala(search)
    }

    override suspend fun getSpecialSellProduct(): Response<List<Kala>> {
        return kalaApi.getSpecialSellProduct()
    }

    override suspend fun createCustomer(customer: Customer): Response<Customer> {
       return kalaApi.createCustomer(customer)
    }

    override suspend fun createOrder(order: Order) {
       kalaApi.createOrder(order)
    }

}