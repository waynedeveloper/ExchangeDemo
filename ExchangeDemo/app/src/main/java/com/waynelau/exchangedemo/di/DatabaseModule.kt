package com.waynelau.exchangedemo.di

import androidx.room.Room
import com.waynelau.exchangedemo.App
import com.waynelau.exchangedemo.data.source.local.database.AppDatabase
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(App.instance, AppDatabase::class.java, "demo.db")
            .build()
    }

    single {
        get<AppDatabase>().currencyDao()
    }
}