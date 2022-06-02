package com.pourkazemi.mahdi.kalastore.data.local

import com.pourkazemi.mahdi.kalastore.data.model.Customer
import com.pourkazemi.mahdi.kalastore.data.model.Kala
import com.pourkazemi.mahdi.kalastore.data.model.Order
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImp @Inject constructor(
    private val customerDao: CustomerDao,
    private val kalaDao: KalaDao
) : LocalDataSource {
    override suspend fun insertCustomer(customer: Customer) {
        customerDao.insertCustomer(customer)
    }

    override suspend fun deleteCustomer(customer: Customer) {
        customerDao.deleteCustomer(customer)
    }

    override fun getAllCustomer(): Flow<List<Customer>> {
        return customerDao.getAllCustomer()
    }

    override suspend fun insertKala(kala: Kala) {
        kalaDao.insertKala(kala)
    }

    override suspend fun deleteKala(kala: Kala) {
        kalaDao.deleteKala(kala)
    }

    override fun getAllKala(): Flow<List<Kala>> {
        return kalaDao.getAllKala()
    }
}