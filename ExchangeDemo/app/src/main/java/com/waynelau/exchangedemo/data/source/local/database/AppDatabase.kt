package com.waynelau.exchangedemo.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.waynelau.exchangedemo.data.model.CurrencyInfo
import com.waynelau.exchangedemo.data.source.local.database.dao.CurrencyDao

@Database(entities = [CurrencyInfo::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
}
