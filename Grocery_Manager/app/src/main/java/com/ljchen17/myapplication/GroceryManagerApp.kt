package com.ljchen17.myapplication

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.ljchen17.myapplication.data.GroceryDetailsAppDatabase
import com.ljchen17.myapplication.data.model.GroceryDetails

class GroceryManagerApp: Application() {
    val db by lazy {
        Room.databaseBuilder(applicationContext, GroceryDetailsAppDatabase::class.java, "grocery_database")
            .createFromAsset("testDatabase.db")
            .build()
    }

    lateinit var stats: LiveData<List<GroceryDetails>>
    lateinit var databaseWorkerManager: DatabaseWorkerManager

    override fun onCreate() {
        super.onCreate()
        databaseWorkerManager = DatabaseWorkerManager(this)
    }
}