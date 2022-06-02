package com.pourkazemi.mahdi.kalastore.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pourkazemi.mahdi.kalastore.data.Repository
import com.pourkazemi.mahdi.kalastore.data.model.Kala
import com.pourkazemi.mahdi.kalastore.data.model.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _dataBaseKala: MutableStateFlow<List<Kala>> =
        MutableStateFlow(listOf())
    val dataBaseKala = _dataBaseKala.asStateFlow()

    init {
        getAllKala()
    }

    private fun getAllKala() {
        viewModelScope.launch {
            repository.getAllKala().collect {
                _dataBaseKala.emit(it)
            }
        }
    }

    fun deleteKala(kala: Kala) {
        viewModelScope.launch {
            repository.deleteKala(kala)
        }
    }

    fun insertKala(kala: Kala) {
        viewModelScope.launch {
            repository.insertKala(kala)
        }
    }
    fun createOrder(order: Order){
        viewModelScope.launch {
            repository.createOrder(order)
        }
    }
}