package com.app.currencyconverter.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = DatabaseConstants.DBEntity.table_currency)
class CurrencyEntity(
    @PrimaryKey @ColumnInfo(name = "currency_type") var currencyType: String,
    @ColumnInfo(name = "currency_amount") var currencyAmount: Double
)