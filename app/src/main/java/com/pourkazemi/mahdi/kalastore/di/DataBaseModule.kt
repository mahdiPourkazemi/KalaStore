package com.pourkazemi.mahdi.kalastore.di

import android.content.Context
import androidx.room.*
import com.pourkazemi.mahdi.kalastore.data.local.CustomerDao
import com.pourkazemi.mahdi.kalastore.data.local.CustomerDataBase
import com.pourkazemi.mahdi.kalastore.data.local.LocalDataSource
import com.pourkazemi.mahdi.kalastore.data.local.LocalDataSourceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun getDatabase(
        @ApplicationContext context: Context
    ): CustomerDataBase = Room.databaseBuilder(
        context.applicationContext,
        CustomerDataBase::class.java,
        "customer_database"
    ).build()

    @Singleton
    @Provides
    fun personDao(
        customerDataBase: CustomerDataBase
    ): CustomerDao = customerDataBase.customerDao()

    @Provides
    fun provideLocalDataSourceImp(
        customerDao: CustomerDao
    ):LocalDataSource{
        return LocalDataSourceImp(customerDao)
    }
}