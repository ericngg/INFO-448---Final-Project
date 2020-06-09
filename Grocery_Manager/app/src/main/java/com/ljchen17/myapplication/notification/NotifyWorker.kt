package com.ljchen17.myapplication.notification

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ljchen17.myapplication.GroceryApplication

class NotifyWorker(private val context: Context, workParams: WorkerParameters): Worker(context , workParams) {

    private val GApp = context.applicationContext as GroceryApplication
    private val groceryNotificationManager =(context.applicationContext as GroceryApplication).groceryNotificationManager

    override fun doWork(): Result {

        // notify expiring item
        var index = inputData.getString("index")
        Log.i("echee", "NotifierWorker: ${index}")
        var grocery = GApp.allGroceries[index?.toInt()!!]
        groceryNotificationManager.sendNotification(grocery)

        return Result.success()
    }
}