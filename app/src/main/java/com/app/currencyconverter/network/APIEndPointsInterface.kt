package com.app.currencyconverter.network

import com.app.currencyconverter.apputils.AppConstants
import com.app.currencyconverter.pojo.LatestCurrencyRatesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIEndPointsInterface {

    @GET(AppConstants.APIEndPoints.latest + ".json")
    suspend fun latest(@Query("app_id") appId : String): LatestCurrencyRatesResponse
}