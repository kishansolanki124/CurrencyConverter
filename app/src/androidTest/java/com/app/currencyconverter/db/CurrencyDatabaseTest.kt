package com.app.currencyconverter.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CurrencyDatabaseTest : TestCase() {

    private lateinit var db: CurrencyDatabase
    private lateinit var dao: CurrencyDao

    @Before
    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, CurrencyDatabase::class.java).build()
        dao = db.getCurrencyDao()
    }

    @After
    fun closeDB() {
        db.close()
    }

    @Test
    fun writeAndReadCurrency() {
        val currencyEntity = CurrencyEntity("USD", 100.00)
        dao.insert(currencyEntity)
        val allCurrencies = dao.getAllCurrencyList()
        assertThat(allCurrencies[0].currencyType == "USD").isTrue()
    }
}