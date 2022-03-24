package com.example.currencyinfo.data.source.remote

import androidx.viewbinding.BuildConfig
import com.example.currencyinfo.domain.model.Rates
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

typealias Token = String

interface ExchangeService {

    @GET("latest")
    suspend fun getRates(
        @Query("access_key") token: Token = "51d0492795fb164361287f70054fb49e"
        ): Response<Rates>
}