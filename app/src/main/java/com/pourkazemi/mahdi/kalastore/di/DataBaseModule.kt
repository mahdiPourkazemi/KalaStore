package com.pourkazemi.mahdi.kalastore.di

import android.content.Context
import androidx.room.*
import com.pourkazemi.mahdi.kalastore.data.local.*
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
    fun provideCustomerDatabase(
        @ApplicationContext context: Context
    ): CustomerDataBase = Room.databaseBuilder(
        context.applicationContext,
        CustomerDataBase::class.java,
        "customer_database"
    ).build()

    @Singleton
    @Provides
    fun provideKalaDatabase(
        @ApplicationContext context: Context
    ): KalaDataBase = Room.databaseBuilder(
        context.applicationContext,
        KalaDataBase::class.java,
        "order_database"
    ).build()


    @Singleton
    @Provides
    fun provideCustomerDao(
        customerDataBase: CustomerDataBase
    ): CustomerDao = customerDataBase.customerDao()

    @Provides
    fun provideKalaDao(
        oderDataBase: KalaDataBase
    ): KalaDao = oderDataBase.kalaDao()

    @Singleton
    @Provides
    fun provideLocalDataSourceImp(
        customerDao: CustomerDao,
        kalaDao: KalaDao
    ): LocalDataSource {
        return LocalDataSourceImp(customerDao,kalaDao)
    }
}