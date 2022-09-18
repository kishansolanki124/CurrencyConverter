package com.app.currencyconverter.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.app.currencyconverter.pojo.CurrencyEntity

@Dao
interface CurrencyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: CurrencyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(note: List<CurrencyEntity>)

    @Delete
    suspend fun delete(note: CurrencyEntity)

    @Query("Select * from currency order by currencyType ASC")
    fun getAllNotes(): LiveData<List<CurrencyEntity>>

    @Update
    suspend fun update(note: CurrencyEntity)
}