package com.pourkazemi.mahdi.kalastore.data.local

import androidx.room.*
import com.pourkazemi.mahdi.kalastore.data.model.Customer
import kotlinx.coroutines.flow.Flow
@Dao
interface CustomerDao {

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertCustomer(customer: Customer)

        @Delete
        suspend fun deleteCustomer(customer: Customer)

        @Query("SELECT * FROM customer")
        fun getAllCustomer(): Flow<List<Customer>>
    }