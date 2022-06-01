package com.pourkazemi.mahdi.kalastore.data.local

import com.pourkazemi.mahdi.kalastore.data.model.Customer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImp @Inject constructor(
    private val customerDao: CustomerDao
):LocalDataSource{
    override suspend fun insertCustomer(customer: Customer) {
       customerDao.insertCustomer(customer)
    }

    override suspend fun deleteCustomer(customer: Customer) {
        customerDao.deleteCustomer(customer)
    }

    override fun getAllCustomer(): Flow<List<Customer>> {
        return customerDao.getAllCustomer()
    }

}