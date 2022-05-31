package com.pourkazemi.mahdi.kalastore.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pourkazemi.mahdi.kalastore.data.Repository
import com.pourkazemi.mahdi.kalastore.data.model.Kala
import com.pourkazemi.mahdi.maktab_hw_18_1.util.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _listOfSearch: MutableStateFlow<ResultWrapper<List<Kala>>> =
        MutableStateFlow(ResultWrapper.Loading)
    val listOfSearch= _listOfSearch.asStateFlow()

    fun search(inputSearch: String) {
        viewModelScope.launch {
            repository.searchListKala(inputSearch).collect{
               _listOfSearch.emit(it)
            }
        }
    }
}