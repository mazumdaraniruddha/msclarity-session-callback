package com.example.sampleclarityinitialise

import android.content.Context
import android.util.Log
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters

class WorkManagerInitializer : Initializer<WorkManager> {

    override fun create(context: Context): WorkManager {
        WorkManager.initialize(
            context, Configuration.Builder()
                .setMinimumLoggingLevel(Log.INFO)
                .setWorkerFactory(WorkManagerFactory())
                .build()
        )
        Log.d("ClarityInit", "WorkManager init done")
        return WorkManager.getInstance(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}

class WorkManagerFactory : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return TestWorker(appContext, workerParameters)
    }
}

class TestWorker(val context: Context, val workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return Result.success()
    }
}