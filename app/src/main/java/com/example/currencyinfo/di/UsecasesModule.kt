package com.example.currencyinfo.di

import com.example.currencyinfo.data.repository.RepositoryImpl
import com.example.currencyinfo.domain.usecases.RatesUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UsecasesModule {

    @ViewModelScoped
    @Provides
    fun provideRatesUseCases(repositoryRates: RepositoryImpl) = RatesUseCases(repositoryRates)

}