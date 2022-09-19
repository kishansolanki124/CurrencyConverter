package com.app.currencyconverter.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencyList: List<CurrencyEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currencyList: CurrencyEntity)

    @Query("Select * from currency order by currencyType ASC")
    fun getAllCurrencies(): LiveData<List<CurrencyEntity>>

    @Query("Select * from currency order by currencyType ASC")
    fun getAllCurrencyList(): List<CurrencyEntity>
}