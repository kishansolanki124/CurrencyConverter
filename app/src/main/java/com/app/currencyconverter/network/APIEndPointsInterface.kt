package com.app.currencyconverter.network

import com.app.currencyconverter.pojo.LatestCurrencyRatesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface APIEndPointsInterface {

    @GET(APIHelperConstants.APIEndPoints.LATEST + ".json")
    suspend fun latest(@Query(APIHelperConstants.APIParams.app_id) appId: String): LatestCurrencyRatesResponse
}