package com.ljchen17.myapplication.notification

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ljchen17.myapplication.GroceryApplication

class HttpsWorker(private val context: Context, workParams: WorkerParameters): Worker(context , workParams) {

    private val apiManager =(context.applicationContext as GroceryApplication).apiManager
    private val groceryNotificationManager =(context.applicationContext as GroceryApplication).groceryNotificationManager

    override fun doWork(): Result {

        // get https connection
        var message: String = apiManager.getMessage()
        if (message.isNotEmpty()) {
            groceryNotificationManager.postMessages(message)
        }

        return Result.success()
    }
}