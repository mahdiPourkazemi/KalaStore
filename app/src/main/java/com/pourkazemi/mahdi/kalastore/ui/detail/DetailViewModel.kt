package com.pourkazemi.mahdi.kalastore.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pourkazemi.mahdi.kalastore.data.Repository
import com.pourkazemi.mahdi.kalastore.data.model.Kala
import com.pourkazemi.mahdi.kalastore.data.model.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: Repository
) :ViewModel(){

   fun insertKala(kala: Kala){
       viewModelScope.launch {
           repository.insertKala(kala)
       }
   }
}