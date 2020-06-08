package com.ljchen17.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import com.ljchen17.myapplication.R
import com.ljchen17.myapplication.data.GroceryDetailsAppDatabase
import com.ljchen17.myapplication.data.GroceryDetailsDao
import com.ljchen17.myapplication.data.model.GroceryDetails

class StatisticsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        //addMockData(db.groceryDetailsDao())
        val test = db.groceryDetailsDao().getAll().value
        if(test != null) {
            Log.i("database42", test[0].toString())
        } else {
            Log.i("database42", "null")
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        val test = db.groceryDetailsDao().getAll().value
        if(test != null) {
            Log.i("database42", test[0].toString())
        } else {
            Log.i("database42", "null")
        }

        removeMockData(db.groceryDetailsDao())
    }

    private fun addMockData(groceryDao: GroceryDetailsDao) {
        groceryDao.insert(mockData)
        Log.i("database42", db.groceryDetailsDao().getAll().value?.get(0).toString())
    }

    private fun removeMockData(groceryDao: GroceryDetailsDao) {
        groceryDao.delete(mockData)
        Log.i("database42", db.groceryDetailsDao().getAll().value?.get(0).toString())
    }
}
