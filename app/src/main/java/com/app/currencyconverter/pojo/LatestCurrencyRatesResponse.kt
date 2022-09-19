package com.app.currencyconverter.pojo

import com.google.gson.JsonObject

data class LatestCurrencyRatesResponse(
    var rates: JsonObject = JsonObject()
)