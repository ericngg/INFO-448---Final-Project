package com.ljchen17.myapplication

import android.content.Context
import androidx.room.Room
import com.ljchen17.myapplication.data.GroceryDetailsAppDatabase

class DatabaseStatsManager(applicationContext: Context) {
    private val db by lazy { Room.databaseBuilder(applicationContext, GroceryDetailsAppDatabase::class.java, "grocery_database")
        .createFromAsset("testDatabase.db")
        .build() }
}