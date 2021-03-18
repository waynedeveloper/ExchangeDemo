package com.waynelau.exchangedemo.data.source.repository

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.waynelau.exchangedemo.App
import com.waynelau.exchangedemo.data.model.CurrencyInfo
import com.waynelau.exchangedemo.data.source.local.database.dao.CurrencyDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext


interface CurrencyRepository {
    suspend fun getCurrencyList(isAsc: Boolean): List<CurrencyInfo>
    suspend fun getCurrencyListByKeyword(keyword: String?): List<CurrencyInfo>
}

class CurrencyRepositoryImpl(private val currencyDao: CurrencyDao) : CurrencyRepository {
    @Synchronized
    override suspend fun getCurrencyList(isAsc: Boolean) = withContext(Dispatchers.IO) {
        // delay(3000)

        if(currencyDao.getCurrencyInfo(true).isEmpty()) {
            // no data, first time access
            // retrieve dummy data from json file
            val fileText: String = App.instance.assets.open("Currency.json").bufferedReader().use {
                it.readText()
            }

            val obj: List<CurrencyInfo> = Gson().fromJson(fileText, object : TypeToken<List<CurrencyInfo>>() {}.type)

            //insert into database
            currencyDao.insertCurrencyInfo(obj)
            obj

        }else {
            // retrieve from database
            currencyDao.getCurrencyInfo(isAsc)
        }
    }

    override suspend fun getCurrencyListByKeyword(keyword: String?) = withContext(Dispatchers.IO) {
        currencyDao.getCurrencyInfoByKeyword(keyword)
    }
}