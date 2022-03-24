package com.example.currencyinfo.data.repository

import com.example.currencyinfo.domain.model.Rates
import com.example.currencyinfo.utils.ResponseWrapper

interface RatesRepository {

    suspend fun getRates(): ResponseWrapper<Rates?>

}