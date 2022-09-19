package com.app.currencyconverter

import android.app.IntentService
import android.content.Intent
import com.app.currencyconverter.apputils.SharedPrefHandler
import com.app.currencyconverter.db.CurrencyDatabase
import com.app.currencyconverter.db.CurrencyEntity
import com.app.currencyconverter.db.CurrencyRepository
import com.app.currencyconverter.network.APIEndPointsInterface
import com.app.currencyconverter.network.RetrofitFactory
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class CurrencyAPIService : IntentService("MyIntentService") {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private lateinit var repository: CurrencyRepository
    private var apiEndPointsInterface =
        RetrofitFactory.createService(APIEndPointsInterface::class.java)

    override fun onHandleIntent(intent: Intent?) {
        val dao = CurrencyDatabase.getDatabase(application).getCurrencyDao()
        repository = CurrencyRepository(dao)

        println("db time: ${SharedPrefHandler.getInstance(
            application,
            SharedPrefHandler.PrefFiles.USER_DETAILS_PREF
        )
            .getLongValue("api time")}")

        println("db time not: ${System.currentTimeMillis() - 60000}")

        if (SharedPrefHandler.getInstance(
                application,
                SharedPrefHandler.PrefFiles.USER_DETAILS_PREF
            )
                .getLongValue("api time") > (System.currentTimeMillis() - 60000)
        ) {
            //30 minutes = 1800000
            //1 nub = 60000
            saveAPIResponseToDBAndReturnUpdatedData()
        }
    }

    private fun saveAPIResponseToDBAndReturnUpdatedData() {
        scope.launch {
            val apiResponse = apiEndPointsInterface.latest("a1980775c93b44898a2844374bf6e552")
            val jsonObject: JsonObject =
                JsonParser().parse(Gson().toJson(apiResponse.rates)).asJsonObject
            val keys = jsonObject.keySet()

            val arrayList = ArrayList<CurrencyEntity>()
            for (currencyType in keys) {
                arrayList.add(
                    CurrencyEntity(
                        currencyType = currencyType,
                        currencyAmount = jsonObject.get(currencyType).toString().toDouble(),
                        createdAt = System.currentTimeMillis(),
                        modifiedAt = System.currentTimeMillis()
                    )
                )
            }

            SharedPrefHandler.getInstance(
                application,
                SharedPrefHandler.PrefFiles.USER_DETAILS_PREF
            )
                .add("api time", System.currentTimeMillis())
            repository.insertAll(
                arrayList
            )
        }
    }
}