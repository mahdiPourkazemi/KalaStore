package com.pourkazemi.mahdi.kalastore.ui.home

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pourkazemi.mahdi.kalastore.data.Repository
import com.pourkazemi.mahdi.kalastore.data.model.Kala
import com.pourkazemi.mahdi.kalastore.utils.NetworkStatus
import com.pourkazemi.mahdi.kalastore.utils.NetworkStatusTracker
import com.pourkazemi.mahdi.maktab_hw_18_1.util.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ShearedViewModel @Inject constructor(
    private val repository: Repository,
    private val networkStatusTracker: NetworkStatusTracker
) : ViewModel() {

    private val _networkState: MutableStateFlow<NetworkStatus> =
        MutableStateFlow(NetworkStatus.Available)
    val networkState = _networkState.asStateFlow()

    private val _splashScreen: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val splashScreen = _splashScreen.asStateFlow()

    private val _listOfPopularKala: MutableStateFlow<ResultWrapper<List<Kala>>> =
        MutableStateFlow(ResultWrapper.Loading)
    val listOfPopularKala = _listOfPopularKala.asStateFlow()

    private val _listOfRatingKala: MutableStateFlow<ResultWrapper<List<Kala>>> =
        MutableStateFlow(ResultWrapper.Loading)
    val listOfRatingKala = _listOfRatingKala.asStateFlow()

    private val _listOfDateKala: MutableStateFlow<ResultWrapper<List<Kala>>> =
        MutableStateFlow(ResultWrapper.Loading)
    val listOfDateKala = _listOfDateKala.asStateFlow()

    init {
        getListProduct()
        viewModelScope.launch {
            delay(2000)
            _splashScreen.emit(false)
        }
        viewModelScope.launch {
            networkStatusTracker.networkStatus.collect {
                _networkState.emit(it)
            }
        }
    }

    fun getListProduct() {
        viewModelScope.launch {
            repository.getListKala(
                "popularity"
            ).collect {
                _listOfPopularKala.emit(it)
            }
        }
        viewModelScope.launch {
            repository.getListKala(
                "rating"
            ).collect {
                _listOfRatingKala.emit(it)
            }
        }
        viewModelScope.launch {
            repository.getListKala(
                "date"
            ).collect {
                _listOfDateKala.emit(it)
            }
        }
    }

/*    private fun checkEnterAppConnection(context: Context): NetworkStatus {
        return if ((context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager?)?.isDefaultNetworkActive == true) {
            NetworkStatus.Available
        } else NetworkStatus.Unavailable
    }*/

}