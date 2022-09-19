package com.app.currencyconverter.apputils

interface AppConstants {

    interface APIEndPoints {
        companion object {
            const val BASE_URL = "https://openexchangerates.org/api/"
            const val latest: String = "latest"
        }
    }
}