package com.app.currencyconverter.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.app.currencyconverter.pojo.CurrencyEntity

@Database(entities = arrayOf(CurrencyEntity::class), version = 1, exportSchema = false)
abstract class CurrencyDatabase : RoomDatabase() {
 
    abstract fun getCurrencyDao(): CurrencyDao
 
    companion object {
        // Singleton prevents multiple
        // instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: CurrencyDatabase? = null
 
        fun getDatabase(context: Context): CurrencyDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CurrencyDatabase::class.java,
                    "currency_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}