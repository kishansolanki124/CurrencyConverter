package com.app.currencyconverter.db

import androidx.lifecycle.LiveData

class CurrencyRepository(private val notesDao: CurrencyDao) {

    val allNotes: LiveData<List<CurrencyEntity>> = notesDao.getAllNotes()

    // on below line we are creating an insert method
    // for adding the note to our database.
//    suspend fun insert(note: CurrencyEntity) {
//        notesDao.insert(note)
//    }

    suspend fun insertAll(note: List<CurrencyEntity>) {
        notesDao.insertAll(note)
    }

//    // on below line we are creating a delete method
//    // for deleting our note from database.
//    suspend fun delete(note: CurrencyEntity){
//        notesDao.delete(note)
//    }

//     // on below line we are creating a update method for
//     // updating our note from database.
//     suspend fun update(note: CurrencyEntity){
//        notesDao.update(note)
//    }
}