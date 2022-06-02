package com.pourkazemi.mahdi.kalastore.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pourkazemi.mahdi.kalastore.data.Repository
import com.pourkazemi.mahdi.kalastore.data.model.Customer
import com.pourkazemi.mahdi.maktab_hw_18_1.util.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _dataBaseCustomer: MutableStateFlow<List<Customer>> =
        MutableStateFlow(listOf())
    val dataBaseCustomer = _dataBaseCustomer.asStateFlow()

    private val _createdUser: MutableStateFlow<ResultWrapper<Customer>> =
        MutableStateFlow(ResultWrapper.Loading)
    val createdUser = _createdUser.asStateFlow()

    init {
        getAllCustomer()
    }

    fun createCustomer(customer: Customer) {
        viewModelScope.launch {
            repository.createCustomer(customer).collect {
                _createdUser.emit(it)
            }
        }
    }

    fun insertCustomer(customer: Customer) {
        viewModelScope.launch {
            repository.insertCustomer(customer)
        }
    }

    private fun getAllCustomer() {
        viewModelScope.launch {
            repository.getAllCustomer().collect {
                _dataBaseCustomer.emit(it)
            }
        }
    }
    fun deleteCustomerFromDataBase(customer: Customer){
        viewModelScope.launch {
            repository.deleteCustomer(customer)
        }
    }
}