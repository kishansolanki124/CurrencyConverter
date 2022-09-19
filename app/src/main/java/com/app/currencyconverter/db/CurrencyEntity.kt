package com.app.currencyconverter.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
class CurrencyEntity(
    @PrimaryKey @ColumnInfo(name = "currencyType") var currencyType: String,
    @ColumnInfo(name = "currencyAmount") var currencyAmount: Double,
    @ColumnInfo(name = "created_at") val createdAt: Long,
    @ColumnInfo(name = "modified_at") val modifiedAt: Long,

)