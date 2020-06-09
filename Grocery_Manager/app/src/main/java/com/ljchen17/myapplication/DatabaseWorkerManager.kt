package com.ljchen17.myapplication

import android.content.Context
import androidx.work.*

class DatabaseWorkerManager(context: Context) {
    private var workManager = WorkManager.getInstance(context)

    companion object {
        const val GM_DOWNLOAD_REQUEST_TAG = "GM_DOWNLOAD_TAG"
        const val GM_UPLOAD_REQUEST_TAG = "GM_UPLOAD_TAG"
    }

    fun downloadData() {
        val workRequest = OneTimeWorkRequestBuilder<DataDownloadWorker>()
            .addTag(GM_DOWNLOAD_REQUEST_TAG)
            .build()

        workManager.enqueue(workRequest)
    }

    fun uploadData() {
        val workRequest = OneTimeWorkRequestBuilder<DataUploadWorker>()
            .addTag(GM_UPLOAD_REQUEST_TAG)
            .build()

        workManager.enqueue(workRequest)
    }
}