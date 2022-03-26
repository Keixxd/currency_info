package com.example.currencyinfo.di

import com.example.currencyinfo.data.repository.RepositoryImpl
import com.example.currencyinfo.data.source.local.FavoriteRatesDao
import com.example.currencyinfo.data.source.remote.ExchangeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(service: ExchangeService, dao: FavoriteRatesDao) = RepositoryImpl(service, dao)

}