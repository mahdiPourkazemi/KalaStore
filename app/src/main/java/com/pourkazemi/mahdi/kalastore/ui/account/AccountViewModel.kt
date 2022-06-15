package com.pourkazemi.mahdi.kalastore.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pourkazemi.mahdi.kalastore.data.Repository
import com.pourkazemi.mahdi.kalastore.data.model.Customer
import com.pourkazemi.mahdi.maktab_hw_18_1.util.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val dataBaseCustomer=repository.getAllCustomer().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = listOf()
    )

    fun createCustomer(customer: Customer) {
        viewModelScope.launch {
            repository.createCustomer(customer).collect {
                when (it) {
                    is ResultWrapper.Loading -> {
                    }
                    is ResultWrapper.Success -> {
                        repository.insertCustomer(it.value)
                    }
                    is ResultWrapper.Error -> {
                    }
                }
            }
        }
    }

    fun deleteCustomerFromDataBase(){
        viewModelScope.launch {
            repository.deleteAllCustomer()
        }
    }
}