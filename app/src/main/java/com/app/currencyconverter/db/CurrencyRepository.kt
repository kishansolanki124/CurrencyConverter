package com.app.currencyconverter.db

import androidx.lifecycle.LiveData

class CurrencyRepository(private val currencyDao: CurrencyDao) {

    val allCurrencies: LiveData<List<CurrencyEntity>> = currencyDao.getAllCurrencies()

    suspend fun insertAll(currencyList: List<CurrencyEntity>) {
        currencyDao.insertAll(currencyList)
    }
}