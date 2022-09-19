package com.app.currencyconverter.db

object DatabaseConstants {

    const val DB_NAME = "currency_database"

    interface DBEntity {
        companion object {
            const val table_currency = "currency"
        }
    }
}