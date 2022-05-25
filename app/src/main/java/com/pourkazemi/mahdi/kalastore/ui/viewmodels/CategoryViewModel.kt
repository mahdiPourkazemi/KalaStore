package com.pourkazemi.mahdi.kalastore.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pourkazemi.mahdi.kalastore.data.Repository
import com.pourkazemi.mahdi.kalastore.data.model.Kala
import com.pourkazemi.mahdi.kalastore.data.model.KalaCategory
import com.pourkazemi.mahdi.maktab_hw_18_1.util.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _categoryList: MutableStateFlow<ResultWrapper<List<KalaCategory>>> =
        MutableStateFlow(ResultWrapper.Loading)
    val categoryList = _categoryList.asStateFlow()

    private val _categorySpecialKalaList: MutableStateFlow<ResultWrapper<List<Kala>>> =
        MutableStateFlow(ResultWrapper.Loading)
    val categorySpecialKalaList = _categorySpecialKalaList.asStateFlow()

    init {
        getListKalaCategory()
    }

   private fun getListKalaCategory() {
        viewModelScope.launch {
            repository.getListKalaCategory().collect {
                _categoryList.emit(it)
            }
        }
    }
   fun getSpecialListKala(category:String){
      viewModelScope.launch {
          repository.getSpecialCategoryListKala(category).collect{
             _categorySpecialKalaList.emit(it)
          }
      }
   }
}