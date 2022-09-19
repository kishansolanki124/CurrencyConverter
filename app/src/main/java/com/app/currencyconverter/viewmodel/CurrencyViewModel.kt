package com.app.currencyconverter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.app.currencyconverter.db.CurrencyDatabase
import com.app.currencyconverter.db.CurrencyEntity
import com.app.currencyconverter.db.CurrencyRepository

class CurrencyViewModel(application: Application) : AndroidViewModel(application) {

    private var currencyList: LiveData<List<CurrencyEntity>>
    private val repository: CurrencyRepository

    init {
        val dao = CurrencyDatabase.getDatabase(application).getCurrencyDao()
        repository = CurrencyRepository(dao)
        currencyList = repository.allCurrencies
    }

    fun currencyList(): LiveData<List<CurrencyEntity>> {
        return currencyList
    }
}