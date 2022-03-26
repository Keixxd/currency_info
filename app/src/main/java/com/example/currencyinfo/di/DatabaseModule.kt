package com.example.currencyinfo.di

import android.content.Context
import com.example.currencyinfo.data.source.local.FavoriteRatesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideRatesDb(@ApplicationContext context: Context): FavoriteRatesDatabase
    = FavoriteRatesDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideDbDao(db: FavoriteRatesDatabase) = db.getDao()
}