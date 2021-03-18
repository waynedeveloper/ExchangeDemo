package com.waynelau.exchangedemo.di

import com.waynelau.exchangedemo.data.source.repository.CurrencyRepository
import com.waynelau.exchangedemo.data.source.repository.CurrencyRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<CurrencyRepository> { CurrencyRepositoryImpl(get()) }
}