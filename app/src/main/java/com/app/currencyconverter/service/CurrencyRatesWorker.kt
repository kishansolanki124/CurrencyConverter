package com.app.currencyconverter.service

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.app.currencyconverter.BuildConfig
import com.app.currencyconverter.apputils.AppConstants
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

/**
 * Work Manager to grab latest currencies from server and update it to DB,
 * API will be called only if last updated before 30 minutes
 */
class CurrencyRatesWorker(appContext: Context, workerParameters: WorkerParameters) :
    Worker(appContext, workerParameters) {

    private val job = SupervisorJob()

    //30 minutes = 1800000
    //1 nub = 60000
    private val minutes30 = 60000//todo work here, change to 30 minute, now its 1 min
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private lateinit var repository: CurrencyRepository
    private var apiEndPointsInterface =
        RetrofitFactory.createService(APIEndPointsInterface::class.java)

    override fun doWork(): Result {
        val dao = CurrencyDatabase.getDatabase(applicationContext).getCurrencyDao()
        repository = CurrencyRepository(dao)

        if (SPreferenceManager.getInstance(applicationContext)
                .getLong(AppConstants.last_sync_time, 0)
            < (System.currentTimeMillis() - minutes30)
        ) {
            saveAPIResponseToDBAndReturnUpdatedData()
        }
        return Result.success()
    }

    private fun saveAPIResponseToDBAndReturnUpdatedData() {
        scope.launch {
            val apiResponse = apiEndPointsInterface.latest(BuildConfig.CURRENCY_APP_ID)
            val jsonObject: JsonObject =
                JsonParser().parse(Gson().toJson(apiResponse.rates)).asJsonObject
            val keys = jsonObject.keySet()

            val arrayList = ArrayList<CurrencyEntity>()
            for (currencyType in keys) {
                try {
                    arrayList.add(
                        CurrencyEntity(
                            currencyType = currencyType,
                            currencyAmount = jsonObject.get(currencyType).toString().toDouble()
                        )
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            SPreferenceManager.getInstance(applicationContext)
                .setLong(AppConstants.last_sync_time, System.currentTimeMillis())
            repository.insertAll(
                arrayList
            )
        }
    }
}