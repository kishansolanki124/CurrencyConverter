package com.app.currencyconverter.network

interface APIHelperConstants {

    interface APIEndPoints {
        companion object {
            const val LATEST: String = "latest"
        }
    }

    interface APIParams {
        companion object {
            const val app_id: String = "app_id"
        }
    }
}