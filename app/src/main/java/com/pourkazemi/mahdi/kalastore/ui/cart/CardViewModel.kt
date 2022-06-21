package com.pourkazemi.mahdi.kalastore.ui.cart

import android.util.Log
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

    private val _listOfOrderKala: MutableStateFlow<ResultWrapper<List<Kala>>> =
        MutableStateFlow(ResultWrapper.Loading)
    val listOfOrderKala = _listOfOrderKala.asStateFlow()

    init {

        val productIdList = mutableListOf<Int>(0)
        viewModelScope.launch {
            repository.getAllCustomer().collect {
                if (it.isNotEmpty()) {
                    it[0].id.let {
                        repository.getOrderList("pending", it).collect {
                            when (it) {
                                is ResultWrapper.Success -> {
                                    it.value.onEach {
                                        it.line_items.onEach {
                                            productIdList.add(it.product_id)
                                        }
                                    }
                                }
                            }

                        }
                    }
                    Log.d("mahdiTest", productIdList.toString())
                    repository.getSpecialProductList(productIdList).collect {
                        _listOfOrderKala.emit(it)
                    }
                }
            }
        }
    }
}