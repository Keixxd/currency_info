package com.example.currencyinfo.utils

sealed class ViewModelWrapper {
    class Success<T>(val result: T): ViewModelWrapper()
    class Error(val error: String?): ViewModelWrapper()
    object Loading : ViewModelWrapper()
    object NotLoading : ViewModelWrapper()
}