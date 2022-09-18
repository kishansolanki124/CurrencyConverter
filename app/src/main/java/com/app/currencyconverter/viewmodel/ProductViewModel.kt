package com.app.currencyconverter.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.app.currencyconverter.db.CurrencyDatabase
import com.app.currencyconverter.db.CurrencyRepository
import com.app.currencyconverter.network.APIEndPointsInterface
import com.app.currencyconverter.network.RetrofitFactory
import com.app.currencyconverter.pojo.CurrencyEntity
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private var updatedDataFromDB: LiveData<List<CurrencyEntity>>
    private var apiEndPointsInterface =
        RetrofitFactory.createService(APIEndPointsInterface::class.java)
    private val repository: CurrencyRepository

    init {
        val dao = CurrencyDatabase.getDatabase(application).getCurrencyDao()
        repository = CurrencyRepository(dao)
        //get data from db
        updatedDataFromDB = repository.allNotes
        /**
         * if(data exist in db) {

        } else {
        saveAPIResponseToDBAndReturnUpdatedData()
        }
         */
        saveAPIResponseToDBAndReturnUpdatedData()
    }

    /**
     * Dispatchers.IO for network or disk operations that takes longer time and runs in background thread
     */
    private fun saveAPIResponseToDBAndReturnUpdatedData() {
        //todo work here for 30 min condition
        /**
         * if(last synced data before 30 minutes) {
        saveAPIResponseToDBAndReturnUpdatedData()
        }
         */
        viewModelScope.launch(Dispatchers.IO) {
            //get data from server
            try {
                val apiResponse = apiEndPointsInterface.latest("a1980775c93b44898a2844374bf6e552")
                //save to DB
                val jsonObject: JsonObject =
                    JsonParser().parse(Gson().toJson(apiResponse.rates)).asJsonObject
                val keys = jsonObject.keySet()

                var arrayList = ArrayList<CurrencyEntity>()
                for (language in keys) {
                    arrayList.add(CurrencyEntity(
                        language,
                        jsonObject.get(language).toString().toDouble()
                    ))
                }

                repository.insertAll(
                    arrayList
                )

//                for (language in keys) {
//                    //save to db
//                    repository.insert(
//                        CurrencyEntity(
//                            language,
//                            jsonObject.get(language).toString().toDouble()
//                        )
//                    )
//                }
                //get data from db
                //updatedDataFromDB = repository.allNotes
                //returnCategoryResponse(apiResponse)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

//    /**
//     * Dispatchers.Main for UI related stuff which runs on Main thread
//     */
//    private suspend fun returnCategoryResponse(eMagazineResponse: LatestCurrencyRatesModel) {
//        withContext(Dispatchers.Main) {
//            mutableCurrencyRatesRModel.value = eMagazineResponse
//        }
//    }

//    /**
//     * Dispatchers.Main for UI related stuff which runs on Main thread
//     */
//    fun currencyRates(): LiveData<LatestCurrencyRatesModel> {
//        return mutableCurrencyRatesRModel
//    }

    /**
     * Dispatchers.Main for UI related stuff which runs on Main thread
     */
    fun getUpdatedDataFromDB(): LiveData<List<CurrencyEntity>> {
        return updatedDataFromDB
    }
}