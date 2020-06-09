package com.ljchen17.myapplication

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class DataDownloadWorker(private val context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    override fun doWork(): Result {
        val app = context.applicationContext as GroceryManagerApp
        app.stats = app.db.groceryDetailsDao().getAll()
        Log.i("database42", app.stats.value.toString())
        return Result.success()
    }
}