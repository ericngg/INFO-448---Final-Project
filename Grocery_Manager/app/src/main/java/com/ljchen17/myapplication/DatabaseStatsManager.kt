package com.ljchen17.myapplication

import android.content.Context
import androidx.room.Room
import com.ljchen17.myapplication.data.GroceryDetailsAppDatabase

class DatabaseStatsManager(applicationContext: Context) {
    private val db by lazy {
        Room.databaseBuilder(applicationContext, GroceryDetailsAppDatabase::class.java, "grocery_database")
        .createFromAsset("testDatabase.db")
        .build()
    }

    
    /*
    //addMockData(db.groceryDetailsDao())
        val test = db.groceryDetailsDao().getAll().value
        if(test != null) {
            Log.i("database42", test[0].toString())
        } else {
            Log.i("database42", "null")
        }

    private fun addMockData(groceryDao: GroceryDetailsDao) {
        groceryDao.insert(mockData)
        Log.i("database42", db.groceryDetailsDao().getAll().value?.get(0).toString())
    }

    private fun removeMockData(groceryDao: GroceryDetailsDao) {
        groceryDao.delete(mockData)
        Log.i("database42", db.groceryDetailsDao().getAll().value?.get(0).toString())
    }

    */
}