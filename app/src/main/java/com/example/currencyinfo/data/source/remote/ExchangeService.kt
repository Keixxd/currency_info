package com.example.currencyinfo.data.source.remote

import com.example.currencyinfo.BuildConfig
import com.example.currencyinfo.domain.model.apientity.Rates
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

typealias Token = String

interface ExchangeService {

    @GET("latest")
    suspend fun getRates(
        @Query("access_key") token: Token = BuildConfig.API_KEY
        ): Response<Rates>
}