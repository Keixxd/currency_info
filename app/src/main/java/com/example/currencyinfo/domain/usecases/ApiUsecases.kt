package com.example.currencyinfo.domain.usecases

import com.example.currencyinfo.data.repository.RepositoryImpl
import javax.inject.Inject

class ApiUsecases @Inject constructor(
    private val repositoryImpl: RepositoryImpl
){

    suspend fun get() = repositoryImpl.getRates()

}