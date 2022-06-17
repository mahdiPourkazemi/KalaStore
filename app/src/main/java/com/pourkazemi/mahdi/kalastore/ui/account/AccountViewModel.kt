package com.pourkazemi.mahdi.kalastore.ui.account

import android.util.Log
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

    val dataBaseCustomer = repository.getAllCustomer().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 100L),
        initialValue = listOf()
    )

    fun createCustomer(customer: Customer) {
        viewModelScope.launch {
            repository.createCustomer(customer).collect {
                when (it) {
                    is ResultWrapper.Loading -> {
                    }
                    is ResultWrapper.Success -> {
                        Log.d("mahdiTest", "this is a problem")
                        repository.insertCustomer(
                            Customer(
                                it.value.id,
                                it.value.email,
                                it.value.first_name,
                                it.value.last_name,
                                it.value.email,
                                "null" //password deserializer fail
                            )
                        )
                    }
                    is ResultWrapper.Error -> {
                    }
                }
            }
        }
    }

    fun deleteCustomerFromDataBase() {
        viewModelScope.launch {
            repository.deleteAllCustomer()
        }
    }
}