package com.ljchen17.myapplication

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ljchen17.myapplication.data.model.GroceryDetails

class DataUploadWorker(private val context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {
    override fun doWork(): Result {
        val app = context.applicationContext as GroceryManagerApp
        val mockData = GroceryDetails(1, null, "NAME", 0, "EXP_DATE", "CATEGORY", "DESC")
        app.db.groceryDetailsDao().insert(mockData)

        Log.i("database42", "upload worker executed")
        return Result.success()
    }
}