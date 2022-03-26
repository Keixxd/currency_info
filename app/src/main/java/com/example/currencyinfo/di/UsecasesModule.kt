package com.example.currencyinfo.di

import com.example.currencyinfo.data.repository.RepositoryImpl
import com.example.currencyinfo.domain.usecases.ApiUsecases
import com.example.currencyinfo.domain.usecases.DatabaseUsecases
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
    fun provideApiUseCases(repositoryRates: RepositoryImpl) = ApiUsecases(repositoryRates)

    @ViewModelScoped
    @Provides
    fun provideDatabaseUseCases(repositoryRates: RepositoryImpl) = DatabaseUsecases(repositoryRates)

}