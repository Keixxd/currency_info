package com.example.currencyinfo.domain.usecases

import com.example.currencyinfo.data.repository.RepositoryImpl
import com.example.currencyinfo.domain.model.roomentity.DbRates
import javax.inject.Inject

class DatabaseUsecases @Inject constructor(
    private val repositoryImpl: RepositoryImpl
){

    suspend fun getDbRates() = repositoryImpl.getDbRates()

    suspend fun addRate(rate: DbRates) = repositoryImpl.addRate(rate)

    suspend fun updateRatesTransaction(rates: List<DbRates>) = repositoryImpl.updateRatesTransaction(rates)

    suspend fun updateRate(rate: DbRates) = repositoryImpl.updateRate(rate)
}