package com.ljchen17.myapplication.manager

import android.content.Context
import android.util.Log
import androidx.work.*
import com.ljchen17.myapplication.notification.NotifyWorker
import java.util.concurrent.TimeUnit

class GroceryWorkingManager(context: Context) {

    private var workManager = WorkManager.getInstance(context)
    var currTag = ""

    fun startNotification(delay: Long, tag: String) {

        currTag = tag
        //Log.i("echee", currTag)
        if (isAERunning()) {
            stopWork()
        }

        val data = Data.Builder()
        data.putString("index", currTag)
        val workRequest = OneTimeWorkRequest.Builder(NotifyWorker::class.java)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .addTag(tag)
            .setInputData(data.build())
            .build()


        workManager.enqueue(workRequest)

    }

    private fun isAERunning(): Boolean {
        //Log.i("echee", currTag)
        return when (workManager.getWorkInfosByTag(currTag).get().firstOrNull()?.state) {
            WorkInfo.State.RUNNING,
            WorkInfo.State.ENQUEUED -> true
            else -> false
        }
    }

    private fun stopWork() {
        workManager.cancelAllWorkByTag(currTag)
    }

//    companion object {
//        const val AE_WORK_REQUEST_TAG = "AE_WORK_REQUEST_TAG"
//    }
}