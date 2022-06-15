package com.pourkazemi.mahdi.kalastore.data.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pourkazemi.mahdi.kalastore.data.model.Customer
import com.pourkazemi.mahdi.kalastore.data.model.Kala
import com.pourkazemi.mahdi.kalastore.data.model.Order
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun insertCustomer(customer: Customer)
    suspend fun deleteCustomer(customer: Customer)
    suspend fun deleteAllCustomer()
    fun getAllCustomer(): Flow<List<Customer>>

    suspend fun insertKala(kala: Kala)
    suspend fun deleteKala(kala:Kala)
    fun getAllKala(): Flow<List<Kala>>
}
