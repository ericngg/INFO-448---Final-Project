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


    }
}
