package com.example.currency.di

import com.example.currency.domain.use_cases.CurrencyUseCases
import com.example.currency.domain.use_cases.GetCurrencyList
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCurrencyUseCases(): CurrencyUseCases{
        return CurrencyUseCases(GetCurrencyList())
    }

}