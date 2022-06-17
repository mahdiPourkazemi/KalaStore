package com.pourkazemi.mahdi.kalastore.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pourkazemi.mahdi.kalastore.data.Repository
import com.pourkazemi.mahdi.kalastore.data.model.Kala
import com.pourkazemi.mahdi.maktab_hw_18_1.util.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _dataBaseKala: MutableStateFlow<List<Kala>> =
        MutableStateFlow(listOf())
    val dataBaseKala = _dataBaseKala.asStateFlow()

    private val _listOfOrderKala: MutableStateFlow<List<Kala>> =
        MutableStateFlow(listOf())
    val listOfOrderKala = _listOfOrderKala.asStateFlow()

    init {

        val productList = mutableListOf<Kala>()
        viewModelScope.launch {
            repository.getAllCustomer().filterNotNull().collectLatest {
                it[0].id.let {
                    repository.getOrderList("pending", it).onCompletion {
                        _listOfOrderKala.emit(productList.toList())
                    }.collect {
                        when (it) {
                            is ResultWrapper.Success -> {
                                it.value.onEach {
                                    it.line_items.onEach {
                                        repository.getSpecialProduct(it.product_id.toString())
                                            .onCompletion { }
                                            .collect {
                                                when (it) {
                                                    is ResultWrapper.Success -> {
                                                        productList.add(it.value)
                                                    }
                                                }
                                            }
                                    }
                                }
                            }
                        }

                    }
                }

            }
        }
    }
}