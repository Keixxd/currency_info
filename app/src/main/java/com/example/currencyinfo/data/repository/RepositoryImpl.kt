package com.example.currencyinfo.data.repository

import com.example.currencyinfo.data.source.remote.ExchangeService
import com.example.currencyinfo.domain.model.Rates
import com.example.currencyinfo.utils.ResponseWrapper
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val service: ExchangeService
) : RatesRepository {

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
}