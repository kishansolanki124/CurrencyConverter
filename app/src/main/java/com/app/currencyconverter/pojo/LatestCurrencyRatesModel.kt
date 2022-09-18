package com.app.currencyconverter.pojo

import com.google.gson.JsonObject

data class LatestCurrencyRatesModel(
    var base: String = "",
    var disclaimer: String = "",
    var license: String = "",
    var rates: JsonObject = JsonObject(),
    var currencyAndTypeList: ArrayList<CurrencyAndTypeList> = ArrayList(),
    var timestamp: Int = 0
) {
    data class CurrencyAndTypeList(
        var currencyAmount: Double = 0.0,
        var currencyType: String = "",
    )
}