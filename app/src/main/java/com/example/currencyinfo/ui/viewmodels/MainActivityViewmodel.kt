package com.example.currencyinfo.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyinfo.domain.usecases.RatesUseCases
import com.example.currencyinfo.utils.ResponseWrapper
import com.example.currencyinfo.utils.ViewModelWrapper
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewmodel @Inject constructor(
    private val ratesUseCases: RatesUseCases
): ViewModel(){

    private val _rates = MutableStateFlow<ViewModelWrapper>(ViewModelWrapper.NotLoading)
    val rates = _rates.asStateFlow()

    fun getRates(){
        viewModelScope.launch(Dispatchers.IO){
            _rates.value = ViewModelWrapper.Loading
            when(val response = ratesUseCases.get()){
                is ResponseWrapper.Success -> _rates.value = ViewModelWrapper.Success(response.data)
                is ResponseWrapper.Failure -> _rates.value = ViewModelWrapper.Error(response.message)
            }
        }
    }

}