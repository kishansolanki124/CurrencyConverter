package com.app.currencyconverter.pojo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
class CurrencyEntity(
    @PrimaryKey @ColumnInfo(name = "currencyType") val currencyType: String,
    @ColumnInfo(name = "currencyAmount") val currencyAmount: Double
)