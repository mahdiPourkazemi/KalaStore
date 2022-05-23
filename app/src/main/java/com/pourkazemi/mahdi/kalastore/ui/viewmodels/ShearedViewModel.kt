package com.pourkazemi.mahdi.kalastore.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pourkazemi.mahdi.kalastore.App.Companion.KEY
import com.pourkazemi.mahdi.kalastore.App.Companion.SECRET
import com.pourkazemi.mahdi.kalastore.data.Repository
import com.pourkazemi.mahdi.kalastore.data.model.Kala
import com.pourkazemi.mahdi.maktab_hw_18_1.util.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShearedViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _listOfPopularKala: MutableStateFlow<ResultWrapper<List<Kala>>> =
        MutableStateFlow(ResultWrapper.Loading)
    val listOfPopularKala = _listOfPopularKala.asStateFlow()

    private val _listOfRatingKala: MutableStateFlow<ResultWrapper<List<Kala>>> =
        MutableStateFlow(ResultWrapper.Loading)
    val listOfRatingKala = _listOfRatingKala.asStateFlow()

    private val _listOfDateKala: MutableStateFlow<ResultWrapper<List<Kala>>> =
        MutableStateFlow(ResultWrapper.Loading)
    val listOfDateKala = _listOfDateKala.asStateFlow()

    fun getListProduct() {
        viewModelScope.launch {
            repository.getListKala(
                "popularity",
                KEY,
                SECRET
            ).collect {
                _listOfPopularKala.emit(it)
            }
        }
        viewModelScope.launch {
            repository.getListKala(
                "rating",
                KEY,
                SECRET
            ).collect {
                _listOfRatingKala.emit(it)
            }
        }
        viewModelScope.launch {
            repository.getListKala(
                "date",
                KEY,
                SECRET
            ).collect {
                _listOfDateKala.emit(it)
            }
        }
    }
}