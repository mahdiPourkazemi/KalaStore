package com.pourkazemi.mahdi.kalastore.utils

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.pourkazemi.mahdi.kalastore.MainActivity
import com.pourkazemi.mahdi.kalastore.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

/*class NotificationWorkManager(
    private val repository: Repository,
    private val appContext: Context,
    workerParams: WorkerParameters
) :
    CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.UK)
        repository.newProductList(date.toString()).let {
            it
        }
        return@withContext Result.success()
    }

    fun createNotification() {*/
/*    val intent = Intent(this, MainActivity::class.java).apply {
            Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(appContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(this, 100)
            //.setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        with(NotificationManagerCompat.from(appContext)) {
            // notificationId is a unique int for each notification that you must define
            notify(100, builder.build())
        }*//*


    }
}*/
