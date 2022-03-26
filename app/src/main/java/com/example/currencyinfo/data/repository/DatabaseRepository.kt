package com.example.currencyinfo.data.repository

import com.example.currencyinfo.domain.model.roomentity.DbRates

interface DatabaseRepository {

    suspend fun getDbRates(): List<DbRates>

    suspend fun addRate(rate: DbRates)

    suspend fun updateRatesTransaction(rates: List<DbRates>)

    suspend fun updateRate(rate: DbRates)
}