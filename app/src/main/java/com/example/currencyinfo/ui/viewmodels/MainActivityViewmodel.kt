package com.example.currencyinfo.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyinfo.domain.model.roomentity.DbRates
import com.example.currencyinfo.domain.usecases.ApiUsecases
import com.example.currencyinfo.domain.usecases.DatabaseUsecases
import com.example.currencyinfo.utils.ResponseWrapper
import com.example.currencyinfo.utils.ViewModelWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewmodel @Inject constructor(
    private val apiUsecases: ApiUsecases,
    private val databaseUsecases: DatabaseUsecases
): ViewModel(){

    private var _rates = MutableStateFlow<ViewModelWrapper>(ViewModelWrapper.NotLoading)
    val rates = _rates.asStateFlow()

    private var _db = MutableStateFlow<List<DbRates>>(emptyList())
    val db = _db.asStateFlow()


    fun getRates(){
        viewModelScope.launch(Dispatchers.IO){
            _rates.value = ViewModelWrapper.Loading
            when(val response = apiUsecases.get()){
                is ResponseWrapper.Success -> _rates.value = ViewModelWrapper.Success(response.data)
                is ResponseWrapper.Failure -> _rates.value = ViewModelWrapper.Error(response.message)
            }
        }
    }

    fun getDatabaseRates() = viewModelScope.launch (Dispatchers.IO) {
        _db.value = databaseUsecases.getDbRates()
    }

    fun updateDbRates(rates: List<DbRates>) = viewModelScope.launch (Dispatchers.IO) {
        databaseUsecases.updateRatesTransaction(rates)
    }

    fun updateSingleRate(rate: DbRates) = viewModelScope.launch (Dispatchers.IO){
        databaseUsecases.updateRate(rate)
    }


}