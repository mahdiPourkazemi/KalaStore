package com.pourkazemi.mahdi.kalastore.data.remote

import com.pourkazemi.mahdi.kalastore.data.model.*
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

    override suspend fun createOrder(customerId: Int, order: Order) {
        kalaApi.createOrder(customerId, order)
    }

    override suspend fun getOrderList(
        status: String, customerId: Int
    ): Response<List<ReceiveOrder>>{
        return kalaApi.getOrderList(status,customerId)
    }

    override suspend fun getListOfReview(product_id: Int): Response<List<Review>> {
        return kalaApi.getListOfReview(product_id)
    }

    override suspend fun createReview(review: Review): Response<Review> {
        return kalaApi.createReview(review)
    }

    override suspend fun newProductList(date: String): Response<List<Kala>> {
        return kalaApi.newProductList(date)
    }

    override suspend fun getSpecialProduct(id: String): Response<Kala> {
       return kalaApi.getSpecialProduct(id)
    }

}