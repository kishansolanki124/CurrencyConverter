package com.app.currencyconverter.service

import android.content.Intent
import androidx.core.app.JobIntentService
import com.app.currencyconverter.apputils.SPreferenceManager
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

//todo delete this class
//class CurrencyAPIService : JobIntentService() {
//
//    private val job = SupervisorJob()
//    private val scope = CoroutineScope(Dispatchers.IO + job)
//    private lateinit var repository: CurrencyRepository
//    private var apiEndPointsInterface =
//        RetrofitFactory.createService(APIEndPointsInterface::class.java)
//
////    override fun onHandleIntent(intent: Intent?) {
////        val dao = CurrencyDatabase.getDatabase(application).getCurrencyDao()
////        repository = CurrencyRepository(dao)
////
////        println(
////            "db time: ${
////                SPreferenceManager.getInstance(application)
////                    .getLong("api time", 0)
//////                SharedPrefHandler.getInstance(
//////                    application,
//////                    SharedPrefHandler.PrefFiles.USER_DETAILS_PREF
//////                )
//////                    .getLongValue("api time")
////            }"
////        )
////
////        println("db time not: ${System.currentTimeMillis() - 60000}")
////
////        if (SPreferenceManager.getInstance(application)
////                .getLong("api time", 0)
////            < (System.currentTimeMillis() - 60000)
////        ) {
////            //30 minutes = 1800000
////            //1 nub = 60000
////            saveAPIResponseToDBAndReturnUpdatedData()
////        }
////    }
//
//    private fun saveAPIResponseToDBAndReturnUpdatedData() {
//        scope.launch {
//            val apiResponse = apiEndPointsInterface.latest("a1980775c93b44898a2844374bf6e552")
//            val jsonObject: JsonObject =
//                JsonParser().parse(Gson().toJson(apiResponse.rates)).asJsonObject
//            val keys = jsonObject.keySet()
//
//            val arrayList = ArrayList<CurrencyEntity>()
//            for (currencyType in keys) {
//                try {
//                    arrayList.add(
//                        CurrencyEntity(
//                            currencyType = currencyType,
//                            currencyAmount = jsonObject.get(currencyType).toString().toDouble()
//                        )
//                    )
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            }
//
////            SharedPrefHandler.getInstance(
////                application,
////                SharedPrefHandler.PrefFiles.USER_DETAILS_PREF
////            )
////                .add("api time", System.currentTimeMillis())
//            SPreferenceManager.getInstance(application)
//                .setLong("api time", System.currentTimeMillis())
//            repository.insertAll(
//                arrayList
//            )
//        }
//    }
//
//    override fun onHandleWork(intent: Intent) {
//        val dao = CurrencyDatabase.getDatabase(application).getCurrencyDao()
//        repository = CurrencyRepository(dao)
//
//        println(
//            "db time: ${
//                SPreferenceManager.getInstance(application)
//                    .getLong("api time", 0)
//            }"
//        )
//
//        println("db time not: ${System.currentTimeMillis() - 60000}")
//
//        if (SPreferenceManager.getInstance(application)
//                .getLong("api time", 0)
//            < (System.currentTimeMillis() - 60000)
//        ) {
//            //30 minutes = 1800000
//            //1 nub = 60000
//            saveAPIResponseToDBAndReturnUpdatedData()
//        }
//    }
//}