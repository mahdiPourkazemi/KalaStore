package com.pourkazemi.mahdi.kalastore.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import com.pourkazemi.mahdi.kalastore.data.Repository
import com.pourkazemi.mahdi.kalastore.data.model.Customer
import com.pourkazemi.mahdi.kalastore.data.model.Kala
import com.pourkazemi.mahdi.kalastore.data.model.Order
import com.pourkazemi.mahdi.kalastore.data.model.Review
import com.pourkazemi.mahdi.maktab_hw_18_1.util.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _ReviewOfProduct: MutableStateFlow<ResultWrapper<List<Review>>> =
        MutableStateFlow(ResultWrapper.Loading)
    val ReviewOfProduct = _ReviewOfProduct.asStateFlow()


    fun createOrder(customerId: Int, order:Order) {
        viewModelScope.launch {
            repository.createOrder(customerId, order)
        }
    }

    fun getListOfReview(product_id: Int) {
        viewModelScope.launch {
            repository.getListOfReview(product_id).collect {
                _ReviewOfProduct.emit(it)
            }
        }
    }

    fun sendReview(review: Review) {
        viewModelScope.launch {
            repository.createReview(review)
        }
    }

    val flowListCustomer = repository.getAllCustomer().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = listOf()
    )
}