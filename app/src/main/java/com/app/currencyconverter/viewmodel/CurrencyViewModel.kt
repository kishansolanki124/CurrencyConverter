package com.app.currencyconverter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.app.currencyconverter.db.CurrencyDatabase
import com.app.currencyconverter.db.CurrencyEntity
import com.app.currencyconverter.db.CurrencyRepository
import com.app.currencyconverter.network.APIEndPointsInterface
import com.app.currencyconverter.network.RetrofitFactory
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyViewModel(application: Application) : AndroidViewModel(application) {

    private var currencyListFromDb: LiveData<List<CurrencyEntity>>
    private var apiEndPointsInterface =
        RetrofitFactory.createService(APIEndPointsInterface::class.java)
    private val repository: CurrencyRepository

    init {
        val dao = CurrencyDatabase.getDatabase(application).getCurrencyDao()
        repository = CurrencyRepository(dao)
        currencyListFromDb = repository.allNotes
        saveAPIResponseToDBAndReturnUpdatedData()
    }

    /**
     * Dispatchers.IO for network or disk operations that takes longer time and runs in background thread
     */
    private fun saveAPIResponseToDBAndReturnUpdatedData() {
        val dbTime = currencyListFromDb.value?.get(0)?.modifiedAt
        val currentTime = System.currentTimeMillis() - 1800000
        println("dbtime is: $dbTime and currentTime is $currentTime")
        if (currencyListFromDb.value.isNullOrEmpty()  || (null != dbTime && dbTime > currentTime)) {
            //todo work here for 30 min condition
            viewModelScope.launch(Dispatchers.IO) {
//                try {
//                    val apiResponse = apiEndPointsInterface.latest("a1980775c93b44898a2844374bf6e552")
//                    val jsonObject: JsonObject =
//                        JsonParser().parse(Gson().toJson(apiResponse.rates)).asJsonObject
//                    val keys = jsonObject.keySet()
//
//                    val arrayList = ArrayList<CurrencyEntity>()
//                    for (currencyType in keys) {
//                        arrayList.add(
//                            CurrencyEntity(
//                                currencyType = currencyType,
//                                currencyAmount = jsonObject.get(currencyType).toString().toDouble(),
//                                createdAt = System.currentTimeMillis(),
//                                modifiedAt = System.currentTimeMillis()
//                            )
//                        )
//                    }
//
//                    repository.insertAll(
//                        arrayList
//                    )
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
            }
        }
    }

    /**
     * Dispatchers.Main for UI related stuff which runs on Main thread
     */
    fun currencyListFromDb(): LiveData<List<CurrencyEntity>> {
        return currencyListFromDb
    }
}