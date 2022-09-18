package com.app.currencyconverter.db

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.currencyconverter.pojo.CurrencyEntity

class CurrencyRepository(private val notesDao: CurrencyDao) {
     
    // on below line we are creating a variable for our list
    // and we are getting all the notes from our DAO class.
    val allNotes: LiveData<List<CurrencyEntity>> = notesDao.getAllNotes()
     
    // on below line we are creating an insert method
    // for adding the note to our database.
    suspend fun insert(note: CurrencyEntity) {
        notesDao.insert(note)
    }

    suspend fun insertAll(note: List<CurrencyEntity>) {
        notesDao.insertAll(note)
    }

    // on below line we are creating a delete method
    // for deleting our note from database.
    suspend fun delete(note: CurrencyEntity){
        notesDao.delete(note)
    }
     
     // on below line we are creating a update method for
     // updating our note from database.
     suspend fun update(note: CurrencyEntity){
        notesDao.update(note)
    }
}