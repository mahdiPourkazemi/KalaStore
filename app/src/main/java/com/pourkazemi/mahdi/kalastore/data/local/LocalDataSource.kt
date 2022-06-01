package com.pourkazemi.mahdi.kalastore.data.local

import com.pourkazemi.mahdi.kalastore.data.model.Customer
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertCustomer(customer: Customer)
    suspend fun deleteCustomer(customer: Customer)
    fun getAllCustomer(): Flow<List<Customer>>
}
