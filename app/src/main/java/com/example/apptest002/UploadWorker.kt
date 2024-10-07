package com.example.apptest002

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import android.util.Log

class UploadWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // ここで実際のアップロード処理などを行う
        Log.d("UploadWorker", "バックグラウンド作業中")
        return Result.success()
    }
}
