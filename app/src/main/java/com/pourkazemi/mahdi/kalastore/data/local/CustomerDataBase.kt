package com.pourkazemi.mahdi.kalastore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pourkazemi.mahdi.kalastore.data.model.Customer

@Database(entities = [Customer::class], version = 1, exportSchema = false)
abstract class CustomerDataBase:RoomDatabase() {
    abstract fun customerDao():CustomerDao
}