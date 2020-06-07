package com.ljchen17.myapplication

import android.app.Application
import android.util.Log
import com.ljchen17.myapplication.data.model.GroceryDetails
import com.ljchen17.myapplication.manager.GroceryNotificationManager
import com.ljchen17.myapplication.manager.GroceryWorkingManager
import java.lang.System.currentTimeMillis
import java.text.SimpleDateFormat
import java.util.*

class GroceryApplication: Application() {

    var allGroceries: List<GroceryDetails> = emptyList()
    var notifiable: Boolean = true

    lateinit var groceryNotificationManager: GroceryNotificationManager
        private set
    lateinit var groceryWorkingManager: GroceryWorkingManager
        private set


    override fun onCreate() {
        super.onCreate()

        groceryWorkingManager = GroceryWorkingManager(this)
        groceryNotificationManager = GroceryNotificationManager(this)
    }

    fun startNotify() {
        // for loop to notify each item
        if (allGroceries.isNotEmpty() && notifiable) {
            for (x in allGroceries.indices) {
                var time = allGroceries[x].expiration
                val cal = Calendar.getInstance()
                val sdf = SimpleDateFormat("MM/dd/yyyy/HH/mm")
                var hour = cal.get(Calendar.HOUR_OF_DAY)
                var min = cal.get(Calendar.MINUTE) + 1
                time = "$time/$hour/$min"
                cal.time = sdf.parse(time)
                val time2 = currentTimeMillis()
                Log.i("echee", "Application: ${x.toString()}")
                groceryWorkingManager.startNotification(cal.timeInMillis - time2, x.toString())
            }
            notifiable = false
        }
    }

    fun test(time: String) {
        var time = "06/06/2020"
        val cal = Calendar.getInstance()
        val sdf = SimpleDateFormat("MM/dd/yyyy/HH/mm")
        var hour = cal.get(Calendar.HOUR_OF_DAY)
        var min = cal.get(Calendar.MINUTE) + 1
        time = "$time/$hour/$min"
        cal.time = sdf.parse(time)
        val time2 = currentTimeMillis()
        Log.i("echee", "${cal.timeInMillis}, ${time2}")
    }

}