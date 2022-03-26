package com.example.currencyinfo.data.repository

import com.example.currencyinfo.data.source.local.FavoriteRatesDao
import com.example.currencyinfo.data.source.remote.ExchangeService
import com.example.currencyinfo.domain.model.apientity.Rates
import com.example.currencyinfo.domain.model.roomentity.DbRates
import com.example.currencyinfo.utils.ResponseWrapper
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val service: ExchangeService,
    private val dao: FavoriteRatesDao
) : RatesRepository, DatabaseRepository {

    private fun <T> wrapResult(response: Response<T>): ResponseWrapper<T?> {
        return if(response.isSuccessful){
            ResponseWrapper.Success(response.body())
        }else{
            ResponseWrapper.Failure(response.message())
        }
    }


    override suspend fun getRates(): ResponseWrapper<Rates?> {
        return try{
            val response = service.getRates()
            wrapResult(response)
        }catch (e: Exception) {
            ResponseWrapper.Failure(e.message)
        }
    }

    override suspend fun getDbRates() = dao.getRatesFromDb()

    override suspend fun addRate(rate: DbRates) = dao.addRate(rate)

    override suspend fun updateRatesTransaction(rates: List<DbRates>) = dao.updateRatesTransaction(rates)

    override suspend fun updateRate(rate: DbRates) = dao.updateRate(rate)
}