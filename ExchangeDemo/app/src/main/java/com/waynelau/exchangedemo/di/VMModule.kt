package com.waynelau.exchangedemo.di

import com.waynelau.exchangedemo.ui.exchange.CurrencyListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val vmModule = module  {
    viewModel { CurrencyListViewModel(get()) }
}